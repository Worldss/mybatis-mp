package db.sql.api.impl.cmd.struct;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.ConditionFaction;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

public class On implements db.sql.api.cmd.struct.On<On, Dataset, Cmd, Object, Join, ConditionChain>, Cmd {

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
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.ON);
        if (!conditionChain.hasContent()) {
            throw new RuntimeException("ON has no on conditions");
        }
        sqlBuilder = conditionChain().sql(module, this, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.conditionChain);
    }
}
