package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.basic.Condition;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

public class CaseWhen implements Cmd {

    private final Condition condition;

    private final Cmd then;

    public CaseWhen(Condition condition, Cmd then) {
        this.condition = condition;
        this.then = then;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.WHEN);
        sqlBuilder = condition.sql(module, this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.THEN);
        sqlBuilder = then.sql(module, this, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.condition, this.then);
    }
}
