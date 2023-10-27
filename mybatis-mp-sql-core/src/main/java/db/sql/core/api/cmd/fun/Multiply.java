package db.sql.core.api.cmd.fun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.cmd.BasicValue;

import static db.sql.core.api.tookit.SqlConst.MULTIPLY;


public class Multiply extends BasicFunction<Multiply> {

    private final Cmd value;

    public Multiply(Cmd key, Number number) {
        this(key, new BasicValue(number));
    }

    public Multiply(Cmd key, Cmd value) {
        super(MULTIPLY, key);
        this.value = value;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = this.key.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(this.operator);
        sqlBuilder = this.value.sql(this, context, sqlBuilder);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }

}