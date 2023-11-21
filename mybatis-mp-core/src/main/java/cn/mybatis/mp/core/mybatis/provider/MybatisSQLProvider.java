package cn.mybatis.mp.core.mybatis.provider;


import cn.mybatis.mp.core.db.reflect.ResultClassEntityPrefixes;
import cn.mybatis.mp.core.mybatis.mapper.context.*;
import cn.mybatis.mp.core.sql.executor.BaseQuery;
import db.sql.core.api.cmd.basic.Table;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Map;
import java.util.Objects;

public class MybatisSQLProvider {
    public static final String SAVE_NAME = "save";
    public static final String UPDATE_NAME = "update";
    public static final String DELETE_NAME = "delete";
    public static final String QUERY_NAME = "cmdQuery";
    public static final String COUNT_NAME = "cmdCount";
    public static final String QUERY_COUNT_NAME = "countFromQuery";

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

    /**
     * 处理前缀映射
     *
     * @param queryContext
     */
    private static void handlerPrefixMapping(SQLCmdQueryContext queryContext) {
        BaseQuery query = queryContext.getExecution();
        if (Objects.nonNull(query.getReturnType())) {
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
        return queryContext.sql(providerContext.getDatabaseId());
    }

    public static StringBuilder cmdQuery(SQLCmdQueryContext queryContext, ProviderContext providerContext) {
        handlerPrefixMapping(queryContext);
        return queryContext.sql(providerContext.getDatabaseId());
    }

    public static StringBuilder cmdCount(SQLCmdCountQueryContext queryContext, ProviderContext providerContext) {
        return queryContext.sql(providerContext.getDatabaseId());
    }
}
