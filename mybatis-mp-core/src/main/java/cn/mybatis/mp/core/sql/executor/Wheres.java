package cn.mybatis.mp.core.sql.executor;

import db.sql.api.impl.cmd.ConditionFactory;
import db.sql.api.impl.cmd.struct.Where;

public final class Wheres {
    public static Where create() {
        return new Where(new ConditionFactory(new MybatisCmdFactory()));
    }
}
