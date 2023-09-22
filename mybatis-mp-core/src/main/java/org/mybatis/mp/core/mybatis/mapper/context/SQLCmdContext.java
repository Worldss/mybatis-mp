package org.mybatis.mp.core.mybatis.mapper.context;

import db.sql.core.cmd.execution.Execution;

public interface SQLCmdContext<E extends Execution> {

    E getExecution();

    StringBuilder sql(String databaseId);

    Object[] getSQLCmdParams();
}
