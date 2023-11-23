package db.sql.api.impl.cmd.struct;


import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.ConditionFaction;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

public class Where implements db.sql.api.cmd.struct.Where<Where, Cmd, Object, ConditionChain> {

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
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (this.conditionChain == null || !this.conditionChain.hasContent()) {
            return sqlBuilder;
        }
        sqlBuilder = sqlBuilder.append(SqlConst.WHERE);
        this.conditionChain.sql(module, this, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.conditionChain);
    }
}
