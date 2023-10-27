package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.SqlConst;

public class On implements db.sql.api.On<On, Dataset, Cmd, Object, Join, ConditionChain>, Cmd {

    private final ConditionFaction conditionFaction;

    private final Join join;

    private final ConditionChain conditionChain;

    public On(ConditionFaction conditionFaction, Join join) {
        this.conditionFaction = conditionFaction;
        this.join = join;
        conditionChain = new ConditionChain(conditionFaction);
    }

    @Override
    public Join getJoin() {
        return join;
    }

    @Override
    public ConditionChain conditionChain() {
        return conditionChain;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.ON);
        if (!conditionChain.hasContent()) {
            throw new RuntimeException("ON has no on conditions");
        }
        sqlBuilder = conditionChain().sql(user, context, sqlBuilder);
        return sqlBuilder;
    }
}
