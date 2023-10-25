package org.mybatis.mp.core.mybatis.provider;


import db.sql.core.api.cmd.Dataset;
import db.sql.core.api.cmd.Table;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.util.MapUtil;
import org.mybatis.mp.core.db.reflect.FieldInfo;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.mybatis.mapper.MapperTables;
import org.mybatis.mp.core.mybatis.mapper.context.EntityInsertContext;
import org.mybatis.mp.core.mybatis.mapper.context.EntityUpdateContext;
import org.mybatis.mp.core.mybatis.mapper.context.SQLCmdDeleteContext;
import org.mybatis.mp.core.mybatis.mapper.context.SQLCmdQueryContext;
import org.mybatis.mp.core.sql.executor.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class MybatisSQLProvider {

    public static final String SAVE_NAME = "save";
    public static final String UPDATE_NAME = "update";
    public static final String DELETE_NAME = "delete";
    public static final String GET_BY_ID_NAME = "getById";
    public static final String DELETE_BY_ID_NAME = "deleteById";
    public static final String ALL_NAME = "all";

    private static final Map<String, String> SQL_CACHE_MAP = new ConcurrentHashMap<>();

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
                if (dataset instanceof Table) {
                    Table table = (Table) dataset;
                    query.select(query.$().all(table));
                }
            }
        }
        if (query.getReturnType() == null) {
            query.setReturnType(MapperTables.get(providerContext.getMapperType()));
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
