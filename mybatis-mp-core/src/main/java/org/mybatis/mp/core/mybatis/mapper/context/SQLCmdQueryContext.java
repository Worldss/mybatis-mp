package org.mybatis.mp.core.mybatis.mapper.context;

import org.mybatis.mp.core.sql.executor.Query;

public class SQLCmdQueryContext<R> extends BaseSQLCmdContext<Query> {

    public SQLCmdQueryContext(Query execution) {
        super(execution);
    }

    @Override
    public StringBuilder sql(String databaseId) {
        return super.sql(databaseId);
    }
}
