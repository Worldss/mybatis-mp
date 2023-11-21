package db.sql.core.api.cmd.fun;

import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.Cmd;
import db.sql.core.api.tookit.SqlConst;

import java.util.Objects;

public class Rand extends BasicFunction<Rand> {

    private Number max;

    public Rand(Cmd value) {
        this(value, null);
    }

    public Rand(Cmd value, Number max) {
        super(SqlConst.RAND, value);
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        this.key.sql(this, context, sqlBuilder);
        if (Objects.nonNull(this.max)) {
            sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER).append(this.max);
        }
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }
}