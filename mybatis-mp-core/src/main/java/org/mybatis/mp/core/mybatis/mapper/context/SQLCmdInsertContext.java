package org.mybatis.mp.core.mybatis.mapper.context;

import db.sql.core.cmd.execution.InsertExecution;

public class SQLCmdInsertContext<T extends InsertExecution> extends BaseSQLCmdContext<T> {

    public SQLCmdInsertContext(T t) {
        super(t);
    }

}
