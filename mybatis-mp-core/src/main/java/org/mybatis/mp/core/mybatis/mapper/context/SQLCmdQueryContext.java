package org.mybatis.mp.core.mybatis.mapper.context;

import org.mybatis.mp.core.sql.executor.Query;

public class SQLCmdQueryContext<R> extends BaseSQLCmdContext<Query<R>> {

    public SQLCmdQueryContext(Query<R> execution) {
        super(execution);
    }
}
