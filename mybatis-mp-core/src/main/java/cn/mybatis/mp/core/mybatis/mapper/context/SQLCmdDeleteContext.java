package cn.mybatis.mp.core.mybatis.mapper.context;


import cn.mybatis.mp.core.sql.executor.BaseDelete;

public class SQLCmdDeleteContext extends BaseSQLCmdContext<BaseDelete> {

    public SQLCmdDeleteContext(BaseDelete delete) {
        super(delete);
        if (delete.getWhere() == null || !delete.getWhere().hasContent()) {
            throw new RuntimeException("delete has on where condition content ");
        }
    }
}
