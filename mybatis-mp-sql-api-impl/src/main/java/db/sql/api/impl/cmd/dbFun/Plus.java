package db.sql.api.impl.cmd.dbFun;

import db.sql.api.SqlBuilderContext;
import db.sql.api.Cmd;
import db.sql.api.tookit.CmdUtils;
import db.sql.api.impl.cmd.basic.BasicValue;

import static db.sql.api.impl.tookit.SqlConst.PLUS;

public class Plus extends BasicFunction<Plus> {

    private final Cmd value;

    public Plus(Cmd key, Number number) {
        this(key, new BasicValue(number));
    }

    public Plus(Cmd key, Cmd value) {
        super(PLUS, key);
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
