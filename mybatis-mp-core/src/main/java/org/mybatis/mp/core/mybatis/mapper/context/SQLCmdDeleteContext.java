package org.mybatis.mp.core.mybatis.mapper.context;


import db.sql.core.api.cmd.executor.Delete;

public class SQLCmdDeleteContext extends BaseSQLCmdContext {

    public SQLCmdDeleteContext(Delete delete) {
        super(delete);
    }
}
