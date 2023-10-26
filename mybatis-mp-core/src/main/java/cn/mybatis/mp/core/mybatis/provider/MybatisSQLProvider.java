package cn.mybatis.mp.core.mybatis.provider;


import cn.mybatis.mp.core.db.reflect.FieldInfo;
import cn.mybatis.mp.core.mybatis.mapper.context.SQLCmdDeleteContext;
import db.sql.core.api.cmd.Dataset;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.util.MapUtil;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.TableInfos;
import cn.mybatis.mp.core.mybatis.mapper.MapperTables;
import cn.mybatis.mp.core.mybatis.mapper.context.EntityInsertContext;
import cn.mybatis.mp.core.mybatis.mapper.context.EntityUpdateContext;
import cn.mybatis.mp.core.mybatis.mapper.context.SQLCmdQueryContext;
import cn.mybatis.mp.core.sql.executor.Query;
import cn.mybatis.mp.core.util.FieldUtils;
import cn.mybatis.mp.db.annotations.NestedResultTable;
import cn.mybatis.mp.db.annotations.ResultTable;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class MybatisSQLProvider {

    private static class EntityPrefix{

        private final Class entity;

        private final String columnPrefix;

        public EntityPrefix(Class entity,String columnPrefix){
            this.entity=entity;
            this.columnPrefix=columnPrefix;
        }

        public Class getEntity() {
            return entity;
        }

        public String getColumnPrefix() {
            return columnPrefix;
        }
    }

    public static final String SAVE_NAME = "save";
    public static final String UPDATE_NAME = "update";
    public static final String DELETE_NAME = "delete";
    public static final String GET_BY_ID_NAME = "getById";
    public static final String DELETE_BY_ID_NAME = "deleteById";
    public static final String ALL_NAME = "all";

    private static final Map<String, String> SQL_CACHE_MAP = new ConcurrentHashMap<>();

    private static final Map<Class, List<EntityPrefix>> RESULT_TABLE_MAP = new HashMap<>();

    private MybatisSQLProvider() {

    }

    public static StringBuilder save(EntityInsertContext insertContext, ProviderContext providerContext) {
        return insertContext.sql(providerContext.getDatabaseId());
    }

    public static StringBuilder update(EntityUpdateContext updateContext, ProviderContext providerContext) {
        return updateContext.sql(providerContext.getDatabaseId());
    }

    public static StringBuilder delete(SQLCmdDeleteContext deleteContext, ProviderContext providerContext) {
        return deleteContext.sql(providerContext.getDatabaseId());
    }

    private static SQL getTableDefaultSelect(TableInfo tableInfo) {
        return new SQL() {{
            Predicate<FieldInfo> filter = (item) -> {
                return item.getFieldAnnotation() == null || item.getFieldAnnotation().select();
            };
            FROM(tableInfo.getBasicInfo().getSchemaAndTableName());
            tableInfo.getFieldInfos().stream().filter(filter).forEach(item -> {
                SELECT(item.getColumnName());
            });
        }};
    }


    private static String getByIdSql(TableInfo tableInfo) {
        return MapUtil.computeIfAbsent(SQL_CACHE_MAP, tableInfo.getBasicInfo().getEntityClass() + ".getById", (key) -> {
            return getTableDefaultSelect(tableInfo).WHERE(tableInfo.getIdInfo().getColumnName() + "=#{value}").toString();
        });
    }


    public static String getById(Serializable id, ProviderContext context) {
        TableInfo tableInfo = TableInfos.get(MapperTables.get(context.getMapperType()));
        if (tableInfo.getIdInfo() == null) {
            throw new RuntimeException("ID not found");
        }
        return getByIdSql(tableInfo);
    }

    public static String deleteById(Serializable id, ProviderContext context) {
        TableInfo tableInfo = TableInfos.get(MapperTables.get(context.getMapperType()));
        if (Objects.isNull(tableInfo.getIdInfo())) {
            throw new RuntimeException("ID not found");
        }
        return getDeleteByIdSql(tableInfo);
    }

    private static String getDeleteByIdSql(TableInfo tableInfo) {
        return MapUtil.computeIfAbsent(SQL_CACHE_MAP, tableInfo.getBasicInfo().getEntityClass() + ".deleteById", (key) -> {
            return new SQL() {{
                DELETE_FROM(tableInfo.getBasicInfo().getSchemaAndTableName());
                WHERE(tableInfo.getIdInfo().getColumnName() + "=#{value}");
            }}.toString();
        });
    }

    public static String all(ProviderContext context) {
        TableInfo tableInfo = TableInfos.get(MapperTables.get(context.getMapperType()));
        return getTableDefaultSelect(tableInfo).toString();
    }

    private static void fill(SQLCmdQueryContext<?> queryContext, ProviderContext providerContext) {
        Query query = queryContext.getExecution();
        if (Objects.isNull(query.getFrom())) {
            Class tableClass = MapperTables.get(providerContext.getMapperType());
            if (Objects.nonNull(tableClass)) {
                query.from(tableClass);
            }
            if (query.getSelect() == null || query.getSelect().getSelectFiled().isEmpty()) {
                query.select(tableClass);
            }
        }

        if (Objects.nonNull(query.getFrom()) && (Objects.isNull(query.getSelect()) || query.getSelect().getSelectFiled().isEmpty())) {
            List<Dataset> datasets = query.getFrom().getTables();
            for (Dataset dataset : datasets) {
                query.select(query.$().all(dataset));
            }
        }
        if (query.getReturnType() == null) {
            query.setReturnType(MapperTables.get(providerContext.getMapperType()));
        } else {

            List<EntityPrefix> entityPrefix = RESULT_TABLE_MAP.computeIfAbsent(query.getReturnType(), key -> {
                List<EntityPrefix> list=new ArrayList<>();
                ResultTable[] resultTables = (ResultTable[]) query.getReturnType().getAnnotationsByType(ResultTable.class);
                if(resultTables==null||resultTables.length<1){
                    return list;
                }
                for(ResultTable resultTable:resultTables){
                    if(resultTable.columnPrefix()==null||resultTable.columnPrefix().isEmpty()){
                        continue;
                    }
                    list.add(new EntityPrefix(resultTable.value(),resultTable.columnPrefix()));
                }
                FieldUtils.getResultMappingFields(query.getReturnType()).stream().forEach(item->{
                    if(item.isAnnotationPresent(NestedResultTable.class)){
                        NestedResultTable nestedResultTable=item.getAnnotation(NestedResultTable.class);
                        list.add(new EntityPrefix(nestedResultTable.target(),nestedResultTable.columnPrefix()));
                    }
                });
                return list;
            });

            if (entityPrefix != null) {
                for (EntityPrefix item : entityPrefix) {
                    if (item.getColumnPrefix() == null || item.getColumnPrefix().trim().isEmpty()) {
                        continue;
                    }
                    queryContext.getExecution().$().cacheTable(item.getEntity(), 1).setPrefix(item.getColumnPrefix());
                }
            }
        }
    }

    public static StringBuilder countCmdQuery(SQLCmdQueryContext<?> queryContext, ProviderContext providerContext) {
        fill(queryContext, providerContext);
        StringBuilder sql = queryContext.sql(providerContext.getDatabaseId());
        //sql = sql.insert(sql.indexOf("SELECT") + 7, "count(");
        //sql = sql.insert(sql.indexOf("FROM") - 1, ")");
        sql.replace(sql.indexOf("SELECT") + 7, sql.indexOf("FROM") - 1, "count(*)");
        return sql;
    }

    public static StringBuilder cmdQuery(SQLCmdQueryContext<?> queryContext, ProviderContext providerContext) {
        fill(queryContext, providerContext);
        return queryContext.sql(providerContext.getDatabaseId());
    }
}
