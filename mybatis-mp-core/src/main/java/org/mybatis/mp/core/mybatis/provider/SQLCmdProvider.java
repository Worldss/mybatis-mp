package org.mybatis.mp.core.mybatis.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;

public class SQLCmdProvider {
    public static String selectWithCmdQuery(SQLCmdQueryContext queryContext, ProviderContext providerContext) {
        System.out.println(">>>>"+queryContext.sql(providerContext.getDatabaseId())+queryContext.getSQLCmdParams()[0]);
        return queryContext.sql(providerContext.getDatabaseId());
    }
}
