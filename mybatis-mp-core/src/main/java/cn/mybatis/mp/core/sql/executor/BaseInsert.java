package cn.mybatis.mp.core.sql.executor;

import cn.mybatis.mp.core.sql.MybatisCmdFactory;
import db.sql.api.impl.cmd.executor.AbstractInsert;

public abstract class BaseInsert<T extends BaseInsert> extends AbstractInsert<T, MybatisCmdFactory> {
    public BaseInsert() {
        super(new MybatisCmdFactory());
    }
}
