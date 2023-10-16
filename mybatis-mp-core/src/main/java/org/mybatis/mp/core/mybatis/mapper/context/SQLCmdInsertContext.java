package org.mybatis.mp.core.mybatis.mapper.context;


import db.sql.core.api.cmd.executor.Executor;

public class SQLCmdInsertContext<T extends Executor> extends BaseSQLCmdContext<T> {

    public SQLCmdInsertContext(T t) {
        super(t);
    }

}
