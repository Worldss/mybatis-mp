package org.mybatis.mp.core.mybatis.mapper.context;


import org.mybatis.mp.core.sql.executor.Delete;

public class SQLCmdDeleteContext extends BaseSQLCmdContext {

    public SQLCmdDeleteContext(Delete delete) {
        super(delete);
    }
}
