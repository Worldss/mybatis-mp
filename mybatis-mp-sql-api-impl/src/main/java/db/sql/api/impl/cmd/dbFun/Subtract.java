package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.tookit.CmdUtils;

import static db.sql.api.impl.tookit.SqlConst.SUBTRACT;

public class Subtract extends BasicFunction<Subtract> {

    private final Cmd value;

    public Subtract(Cmd key, Number number) {
        this(key, Methods.convert(number));
    }

    public Subtract(Cmd key, Cmd value) {
        super(SUBTRACT, key);
        this.value = value;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        this.key.sql(module, this, context, sqlBuilder);
        sqlBuilder.append(this.operator);
        this.value.sql(module, this, context, sqlBuilder);
        appendAlias(module, parent, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.key, this.value);
    }
}