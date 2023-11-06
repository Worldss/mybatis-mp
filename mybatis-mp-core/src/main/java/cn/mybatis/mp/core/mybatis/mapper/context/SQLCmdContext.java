package cn.mybatis.mp.core.mybatis.mapper.context;


import db.sql.core.api.cmd.executor.Executor;

public interface SQLCmdContext<E extends Executor> {

    E getExecution();

    StringBuilder sql(String dbType);

    Object[] getSQLCmdParams();
}
