package db.sql.api.impl.cmd.condition;

import db.sql.api.SqlBuilderContext;
import db.sql.api.Cmd;
import db.sql.api.cmd.basic.Condition;
import db.sql.api.tookit.CmdUtils;
import db.sql.api.impl.tookit.SqlConst;

public class NotExists implements Condition, Cmd {

    private final Cmd notExistsCmd;

    public NotExists(Cmd notExistsCmd) {
        this.notExistsCmd = notExistsCmd;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.NOT_EXISTS);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_LEFT);
        sqlBuilder = notExistsCmd.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.notExistsCmd);
    }
}
