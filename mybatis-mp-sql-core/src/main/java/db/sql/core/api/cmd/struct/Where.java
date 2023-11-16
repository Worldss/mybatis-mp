package db.sql.core.api.cmd.struct;


import db.sql.api.cmd.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.cmd.ConditionFaction;
import db.sql.core.api.tookit.SqlConst;

public class Where implements db.sql.api.cmd.struct.Where<Where, Cmd, Object, ConditionChain>, Cmd {

    private final ConditionFaction conditionFaction;

    private ConditionChain conditionChain;

    public Where(ConditionFaction conditionFaction) {
        this.conditionFaction = conditionFaction;
    }

    @Override
    public ConditionChain conditionChain() {
        if (this.conditionChain == null) {
            this.conditionChain = new ConditionChain(conditionFaction);
        }
        return conditionChain;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (this.conditionChain == null || !this.conditionChain.hasContent()) {
            return sqlBuilder;
        }
        sqlBuilder = sqlBuilder.append(SqlConst.WHERE);
        this.conditionChain.sql(this, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.conditionChain);
    }
}
