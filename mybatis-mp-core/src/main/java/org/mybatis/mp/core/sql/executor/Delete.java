package org.mybatis.mp.core.sql.executor;

import db.sql.core.api.cmd.ConditionFaction;
import db.sql.core.api.cmd.executor.AbstractDelete;

public class Delete extends AbstractDelete<Delete, MybatisCmdFactory> {
    public Delete() {
        super(MybatisCmdFactory.INSTANCE, ConditionFaction.INSTANCE, new MybatisLambdaCmdFactory());
    }
}
