package org.mybatis.mp.core.sql.executor;

import db.sql.core.api.cmd.ConditionFaction;
import db.sql.core.api.cmd.executor.AbstractUpdate;

public class Update extends AbstractUpdate<Update, MybatisCmdFactory> {
    public Update() {
        super(MybatisCmdFactory.INSTANCE, ConditionFaction.INSTANCE, new MybatisLambdaCmdFactory());
    }
}
