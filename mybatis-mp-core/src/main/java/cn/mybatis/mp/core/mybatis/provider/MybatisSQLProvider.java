package cn.mybatis.mp.core.mybatis.provider;


import cn.mybatis.mp.core.db.reflect.ResultClassEntityPrefixes;
import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.mybatis.mapper.MapperEntitys;
import cn.mybatis.mp.core.mybatis.mapper.context.*;
import cn.mybatis.mp.core.sql.executor.BaseQuery;
import db.sql.core.api.cmd.basic.Dataset;
import db.sql.core.api.cmd.basic.Table;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.util.MapUtil;

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

    private static final Map<String, String> SQL_CACHE_MAP = new ConcurrentHashMap<>();

    private MybatisSQLProvider() {

    }

    public static StringBuilder save(SQLCmdInsertContext insertContext, ProviderContext providerContext) {
        return insertContext.sql(providerContext.getDatabaseId());
    }

    public static StringBuilder update(SQLCmdUpdateContext updateContext, ProviderContext providerContext) {
        return updateContext.sql(providerContext.getDatabaseId());
    }

    public static StringBuilder delete(SQLCmdDeleteContext deleteContext, ProviderContext providerContext) {
        return deleteContext.sql(providerContext.getDatabaseId());
    }

    private static SQL getTableDefaultSelect(TableInfo tableInfo) {
        return new SQL() {{
            Predicate<TableFieldInfo> filter = (item) -> {
                return item.getTableFieldAnnotation() == null || item.getTableFieldAnnotation().select();
            };
            FROM(tableInfo.getSchemaAndTableName());
            tableInfo.getTableFieldInfos().stream().filter(filter).forEach(item -> {
                SELECT(item.getColumnName());
            });
        }};
    }


    private static String getByIdSql(TableInfo tableInfo) {
        return MapUtil.computeIfAbsent(SQL_CACHE_MAP, tableInfo.getSchemaAndTableName() + ".getById", (key) -> {
            return getTableDefaultSelect(tableInfo).WHERE(tableInfo.getIdFieldInfo().getColumnName() + "=#{value}").toString();
        });
    }


    public static String getById(Serializable id, ProviderContext context) {
        TableInfo tableInfo = Tables.get(MapperEntitys.get(context.getMapperType()));
        if (tableInfo.getIdFieldInfo() == null) {
            throw new RuntimeException("ID not found");
        }
        return getByIdSql(tableInfo);
    }

    private static void fill(SQLCmdQueryContext queryContext, ProviderContext providerContext) {
        BaseQuery query = queryContext.getExecution();
        if (Objects.isNull(query.getFrom())) {
            Class tableClass = MapperEntitys.get(providerContext.getMapperType());
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
            query.setReturnType(MapperEntitys.get(providerContext.getMapperType()));
        } else {
            Map<Class, String> entityPrefixMap = ResultClassEntityPrefixes.getEntityPrefix(query.getReturnType());
            if (Objects.nonNull(entityPrefixMap)) {
                entityPrefixMap.forEach((key, value) -> {
                    for (int i = 1; i < 5; i++) {
                        Table table = queryContext.getExecution().$().cacheTable(key, i);
                        if (Objects.nonNull(table)) {
                            table.setPrefix(value);
                            break;
                        }
                    }
                });
            }
        }
    }


    public static StringBuilder countFromQuery(SQLCmdCountFromQueryContext queryContext, ProviderContext providerContext) {
        fill(queryContext, providerContext);
        return queryContext.sql(providerContext.getDatabaseId());
    }

    public static StringBuilder cmdQuery(SQLCmdQueryContext queryContext, ProviderContext providerContext) {
        fill(queryContext, providerContext);
        return queryContext.sql(providerContext.getDatabaseId());
    }

    public static StringBuilder cmdCount(SQLCmdCountQueryContext queryContext, ProviderContext providerContext) {
        fill(queryContext, providerContext);
        return queryContext.sql(providerContext.getDatabaseId());
    }
}
