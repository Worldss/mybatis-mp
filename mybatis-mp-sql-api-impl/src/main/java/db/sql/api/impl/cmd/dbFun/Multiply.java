package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.tookit.CmdUtils;

import static db.sql.api.impl.tookit.SqlConst.MULTIPLY;


public class Multiply extends BasicFunction<Multiply> {

    private final Cmd value;

    public Multiply(Cmd key, Number number) {
        this(key, Methods.convert(number));
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

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.key, this.value);
    }
}