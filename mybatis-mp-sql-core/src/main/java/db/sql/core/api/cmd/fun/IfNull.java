package db.sql.core.api.cmd.fun;

import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.Cmd;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.tookit.SqlConst;

import static db.sql.core.api.tookit.SqlConst.IFNULL;

public class IfNull extends BasicFunction<IfNull> {

    private final Cmd value;

    public IfNull(Cmd key, Cmd value) {
        super(IFNULL, key);
        this.value = value;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        this.key.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
        this.value.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.key, this.value);
    }
}
