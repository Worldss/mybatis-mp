package db.sql.core.api.cmd.fun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.cmd.Condition;
import db.sql.core.api.tookit.SqlConst;

public class CaseWhen implements Cmd {

    private final Condition condition;

    private final Cmd then;

    public CaseWhen(Condition condition, Cmd then) {
        this.condition = condition;
        this.then = then;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.WHEN);
        sqlBuilder = condition.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.THEN);
        sqlBuilder = then.sql(this, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.condition, this.then);
    }
}
