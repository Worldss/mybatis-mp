package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.tookit.SqlConst;

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
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        sqlBuilder = this.key.sql(module, this, context, sqlBuilder);
        if (Objects.nonNull(this.max)) {
            sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER).append(this.max);
        }
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(module, parent, sqlBuilder);
        return sqlBuilder;
    }
}