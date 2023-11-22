package db.sql.api.impl.cmd.condition;

import db.sql.api.SqlBuilderContext;
import db.sql.api.Cmd;
import db.sql.api.cmd.basic.Condition;
import db.sql.api.tookit.CmdUtils;
import db.sql.api.impl.tookit.SqlConst;

public class Exists implements Condition, Cmd {

    private final Cmd existsCmd;

    public Exists(Cmd existsCmd) {
        this.existsCmd = existsCmd;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.EXISTS);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_LEFT);
        sqlBuilder = existsCmd.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.existsCmd);
    }
}
