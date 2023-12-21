package db.sql.api.impl.cmd.condition;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

public class NotExists implements ICondition, Cmd {

    private final Cmd notExistsCmd;

    public NotExists(Cmd notExistsCmd) {
        this.notExistsCmd = notExistsCmd;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(SqlConst.NOT_EXISTS);
        sqlBuilder.append(SqlConst.BRACKET_LEFT);
        notExistsCmd.sql(module, this, context, sqlBuilder);
        sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.notExistsCmd);
    }
}
