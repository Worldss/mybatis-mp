package db.sql.core.api.cmd.fun;

import db.sql.api.cmd.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.SqlConst;

import static db.sql.core.api.tookit.SqlConst.POW;

public class Pow extends BasicFunction<Pow> {

    private final int n;

    public Pow(Cmd key, int n) {
        super(POW, key);
        this.n = n;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        this.key.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER).append(this.n);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }

}