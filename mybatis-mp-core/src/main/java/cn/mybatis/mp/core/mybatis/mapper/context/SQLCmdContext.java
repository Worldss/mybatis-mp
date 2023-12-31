package cn.mybatis.mp.core.mybatis.mapper.context;


import db.sql.api.impl.cmd.executor.Executor;

public interface SQLCmdContext<E extends Executor> {

    E getExecution();

    String sql(String dbType);

    Object[] getSQLCmdParams();
}
