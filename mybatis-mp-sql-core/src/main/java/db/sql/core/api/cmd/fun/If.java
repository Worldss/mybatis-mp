package db.sql.core.api.cmd.fun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.cmd.BasicValue;
import db.sql.core.api.cmd.Condition;
import db.sql.core.api.tookit.SqlConst;

import java.io.Serializable;

import static db.sql.core.api.tookit.SqlConst.IF;

public class If extends BasicFunction<If> {

    private final Cmd value;

    private final Cmd value2;

    public If(Condition condition, Serializable value, Serializable value2) {
        this(condition, new BasicValue(value), new BasicValue(value2));
    }

    public If(Condition condition, Cmd value, Serializable value2) {
        this(condition, value, new BasicValue(value2));
    }

    public If(Condition condition, Cmd value, Cmd value2) {
        super(IF, condition);
        this.value = value;
        this.value2 = value2;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        this.key.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
        this.value.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
        this.value2.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.key, this.value, this.value2);
    }
}
