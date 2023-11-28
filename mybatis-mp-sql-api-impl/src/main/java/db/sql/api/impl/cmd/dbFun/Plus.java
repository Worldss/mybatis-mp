package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.tookit.CmdUtils;

import static db.sql.api.impl.tookit.SqlConst.PLUS;

public class Plus extends BasicFunction<Plus> {

    private final Cmd value;

    public Plus(Cmd key, Number number) {
        this(key, Methods.convert(number));
    }

    public Plus(Cmd key, Cmd value) {
        super(PLUS, key);
        this.value = value;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = this.key.sql(module, this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(this.operator);
        sqlBuilder = this.value.sql(module, this, context, sqlBuilder);
        sqlBuilder = appendAlias(module, parent, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.key, this.value);
    }
}
