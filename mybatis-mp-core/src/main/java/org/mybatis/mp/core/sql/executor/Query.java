package org.mybatis.mp.core.sql.executor;

import db.sql.core.api.cmd.ConditionFaction;

public class Query extends db.sql.core.api.cmd.executor.AbstractQuery<Query, MybatisCmdFactory> {

    private final Class returnType;

    public Query(Class returnType) {
        super(MybatisCmdFactory.INSTANCE, ConditionFaction.INSTANCE, new MybatisLambdaCmdFactory());
        this.returnType = returnType;
    }

    public Class getReturnType() {
        return returnType;
    }
}
