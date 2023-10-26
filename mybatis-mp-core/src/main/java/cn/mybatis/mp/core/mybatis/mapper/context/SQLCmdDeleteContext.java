package cn.mybatis.mp.core.mybatis.mapper.context;


import cn.mybatis.mp.core.sql.executor.Delete;

public class SQLCmdDeleteContext extends BaseSQLCmdContext {

    public SQLCmdDeleteContext(Delete delete) {
        super(delete);
    }
}
