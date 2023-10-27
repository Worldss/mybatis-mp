package db.sql.core.api.cmd;


import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.SqlConst;

public class Where implements db.sql.api.Where<Where, Cmd, Object, ConditionChain>, Cmd {

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
}
