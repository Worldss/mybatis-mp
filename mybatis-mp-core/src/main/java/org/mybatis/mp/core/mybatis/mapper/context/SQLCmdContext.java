package org.mybatis.mp.core.mybatis.mapper.context;

import db.sql.core.cmd.execution.Execution;

public interface SQLCmdContext<E extends Execution> {

    public E getExecution();

    public StringBuilder sql(String databaseId);

    public Object[] getSQLCmdParams();
}
