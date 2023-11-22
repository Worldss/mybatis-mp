package cn.mybatis.mp.core.mybatis.mapper.context;


import db.sql.api.impl.cmd.executor.Executor;

public class SQLCmdInsertContext<T extends Executor> extends BaseSQLCmdContext<T> {

    public SQLCmdInsertContext(T t) {
        super(t);
    }

}
