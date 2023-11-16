package db.sql.core.api.cmd.fun;

import db.sql.api.cmd.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.SqlConst;

import static db.sql.core.api.tookit.SqlConst.ROUND;

public class Round extends BasicFunction<Divide> {

    private final int precision;

    public Round(Cmd key, int precision) {
        super(ROUND, key);
        this.precision = precision;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        this.key.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER).append(this.precision);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }

}
