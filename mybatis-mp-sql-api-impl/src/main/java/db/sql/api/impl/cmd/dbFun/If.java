package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.cmd.basic.Condition;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.io.Serializable;

import static db.sql.api.impl.tookit.SqlConst.IF;

public class If extends BasicFunction<If> {

    private final Cmd value;

    private final Cmd thenValue;

    public If(Condition condition, Serializable value, Serializable thenValue) {
        this(condition, Methods.convert(value), Methods.convert(thenValue));
    }

    public If(Condition condition, Cmd value, Serializable thenValue) {
        this(condition, value, Methods.convert(thenValue));
    }

    public If(Condition condition, Serializable value, Cmd thenValue) {
        this(condition, Methods.convert(value), thenValue);
    }

    public If(Condition condition, Cmd value, Cmd thenValue) {
        super(IF, condition);
        this.value = value;
        this.thenValue = thenValue;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        sqlBuilder = this.key.sql(module, this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
        sqlBuilder = this.value.sql(module, this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
        sqlBuilder = this.thenValue.sql(module, this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(module, parent, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.key, this.value, this.thenValue);
    }
}
