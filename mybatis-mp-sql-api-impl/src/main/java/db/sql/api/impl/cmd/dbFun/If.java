package db.sql.api.impl.cmd.dbFun;

import db.sql.api.SqlBuilderContext;
import db.sql.api.Cmd;
import db.sql.api.tookit.CmdUtils;
import db.sql.api.impl.cmd.basic.BasicValue;
import db.sql.api.impl.cmd.basic.Condition;
import db.sql.api.impl.tookit.SqlConst;

import java.io.Serializable;

import static db.sql.api.impl.tookit.SqlConst.IF;

public class If extends BasicFunction<If> {

    private final Cmd value;

    private final Cmd thenValue;

    public If(Condition condition, Serializable value, Serializable thenValue) {
        this(condition, new BasicValue(value), new BasicValue(thenValue));
    }

    public If(Condition condition, Cmd value, Serializable thenValue) {
        this(condition, value, new BasicValue(thenValue));
    }

    public If(Condition condition, Serializable value, Cmd thenValue) {
        this(condition, new BasicValue(value), thenValue);
    }

    public If(Condition condition, Cmd value, Cmd thenValue) {
        super(IF, condition);
        this.value = value;
        this.thenValue = thenValue;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        this.key.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
        this.value.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
        this.thenValue.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.key, this.value, this.thenValue);
    }
}
