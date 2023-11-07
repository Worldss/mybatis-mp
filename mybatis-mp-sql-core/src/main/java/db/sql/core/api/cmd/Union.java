package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.CmdUtils;
import db.sql.core.api.tookit.SqlConst;

public class Union implements Cmd {

    private final String operator;

    private final Cmd unionCmd;

    public Union(Cmd unionCmd) {
        this(SqlConst.UNION, unionCmd);
    }

    public Union(String operator, Cmd unionCmd) {
        this.operator = operator;
        this.unionCmd = unionCmd;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(this.operator);
        sqlBuilder = unionCmd.sql(user, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.unionCmd);
    }
}
