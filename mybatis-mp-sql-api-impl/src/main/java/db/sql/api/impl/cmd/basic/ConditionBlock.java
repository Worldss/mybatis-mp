package db.sql.api.impl.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

public class ConditionBlock implements Cmd {

    private final Connector connector;

    private final ICondition condition;

    public ConditionBlock(Connector connector, ICondition condition) {
        this.connector = connector;
        this.condition = condition;
    }

    public Connector getConnector() {
        return connector;
    }

    public ICondition getCondition() {
        return condition;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(SqlConst.BLANK).append(connector).append(SqlConst.BLANK);
        this.condition.sql(module, this, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.condition);
    }
}
