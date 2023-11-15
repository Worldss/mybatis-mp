package cn.mybatis.mp.core.sql.executor;

import db.sql.core.api.cmd.executor.AbstractInsert;

public class BaseInsert<T extends BaseInsert> extends AbstractInsert<T, MybatisCmdFactory> {
    public BaseInsert() {
        super(new MybatisCmdFactory());
    }
}
