package org.mybatis.mp.core.mybatis.mapper.context;


import db.sql.core.cmd.execution.Update;

public class SQLCmdUpdateContext<T extends Update> extends BaseSQLCmdContext<Update> {

    public SQLCmdUpdateContext(T update) {
        super(update);
    }
}
