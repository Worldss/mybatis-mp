package org.mybatis.mp.core.mybatis.mapper.context;

import org.mybatis.mp.core.sql.executor.LambdaQuery;

public class SQLCmdQueryContext<R> extends BaseSQLCmdContext<LambdaQuery<R>> {

    public SQLCmdQueryContext(LambdaQuery<R> execution) {
        super(execution);
    }
}
