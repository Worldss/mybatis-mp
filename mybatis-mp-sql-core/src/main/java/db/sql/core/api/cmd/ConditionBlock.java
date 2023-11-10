package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.tookit.SqlConst;

public class ConditionBlock implements Cmd {

    private final Connector connector;

    private final db.sql.api.Condition condition;

    public ConditionBlock(Connector connector, db.sql.api.Condition condition) {
        this.connector = connector;
        this.condition = condition;
    }

    public Connector getConnector() {
        return connector;
    }

    public db.sql.api.Condition getCondition() {
        return condition;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.BLANK).append(connector).append(SqlConst.BLANK);
        sqlBuilder = this.condition.sql(user, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.condition);
    }
}
