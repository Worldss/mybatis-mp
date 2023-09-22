package org.mybatis.mp.core.mybatis.provider;

import db.sql.core.cmd.Dataset;
import db.sql.core.cmd.Table;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.util.MapUtil;
import org.mybatis.mp.core.db.reflect.FieldInfo;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.mybatis.mapper.MapperTables;
import org.mybatis.mp.core.mybatis.mapper.context.EntityInsertContext;
import org.mybatis.mp.core.mybatis.mapper.context.EntityUpdateContext;
import org.mybatis.mp.core.mybatis.mapper.context.SQLCmdQueryContext;
import org.mybatis.mp.core.sql.executor.Query;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class MybatisSQLProvider {

    public static final String SAVE_NAME = "save";
    public static final String UPDATE_NAME = "update";
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

    private static SQL getTableDefaultSelect(TableInfo tableInfo) {
        return new SQL() {{
            Predicate<FieldInfo> filter = (item) -> {
                return item.getFieldAnnotation() == null || item.getFieldAnnotation().select();
            };
            FROM(tableInfo.getBasic().getSchemaAndTableName());
            tableInfo.getFieldInfos().stream().filter(filter).forEach(item -> {
                SELECT(item.getColumnName());
            });
        }};
    }


    private static String getByIdSql(TableInfo tableInfo) {
        return MapUtil.computeIfAbsent(SQL_CACHE_MAP, tableInfo.getBasic().getType() + ".getById", (key) -> {
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

    private static String getDeleteByIdSql(TableInfo tableInfo) {
        return MapUtil.computeIfAbsent(SQL_CACHE_MAP, tableInfo.getBasic().getType() + ".deleteById", (key) -> {
            return new SQL() {{
                DELETE_FROM(tableInfo.getBasic().getSchemaAndTableName());
                WHERE(tableInfo.getIdInfo().getColumnName() + "=#{value}");
            }}.toString();
        });
    }


    public static String deleteById(Serializable id, ProviderContext context) {
        TableInfo tableInfo = TableInfos.get(MapperTables.get(context.getMapperType()));
        if (Objects.isNull(tableInfo.getIdInfo())) {
            throw new RuntimeException("ID not found");
        }
        return getDeleteByIdSql(tableInfo);
    }

    public static String all(ProviderContext context) {
        TableInfo tableInfo = TableInfos.get(MapperTables.get(context.getMapperType()));
        return getTableDefaultSelect(tableInfo).toString();
    }

    public static StringBuilder cmdQuery(SQLCmdQueryContext<?> queryContext, ProviderContext providerContext) {
        Query query = queryContext.getExecution();
        if (Objects.isNull(query.getFrom())) {
            Class tableClass = MapperTables.get(providerContext.getMapperType());
            if (Objects.nonNull(tableClass)) {
                query.from(tableClass);
            }
        }

        if (Objects.nonNull(query.getFrom()) && (Objects.isNull(query.getSelect()) || query.getSelect().getCmdList().isEmpty())) {
            Dataset dataset = query.getFrom().getTable();
            if (dataset instanceof Table) {
                Table table = (Table) dataset;
                if (Objects.nonNull(table.getMappingClass())) {
                    query.select(table);
                }
            }
        }
        return queryContext.sql(providerContext.getDatabaseId());
    }
}
