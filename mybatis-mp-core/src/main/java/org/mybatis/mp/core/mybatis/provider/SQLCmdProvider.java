package org.mybatis.mp.core.mybatis.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;

public class SQLCmdProvider {
    public static String selectWithCmdQuery(SQLCmdQueryContext queryContext, ProviderContext providerContext) {
        return queryContext.sql(providerContext.getDatabaseId());
    }
}
