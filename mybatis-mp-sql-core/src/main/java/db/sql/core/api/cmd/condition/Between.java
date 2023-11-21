package db.sql.core.api.cmd.condition;


import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.Cmd;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.cmd.basic.BasicValue;
import db.sql.core.api.tookit.SqlConst;

import java.io.Serializable;

public class Between extends BaseCondition<Cmd, Cmd[]> {

    private final Cmd field;

    private final Cmd[] value;

    public Between(String operator, Cmd field, Cmd value, Cmd value2) {
        super(operator);
        this.field = field;
        this.value = new Cmd[]{value, value2};
    }

    public Between(Cmd key, Cmd value1, Cmd value2) {
        this(SqlConst.BETWEEN, key, value1, value2);
    }

    public Between(Cmd key, Serializable value1, Serializable value2) {
        this(key, new BasicValue(value1), new BasicValue(value2));
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = field.sql(user, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(getOperator());
        sqlBuilder = value[0].sql(user, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.AND);
        sqlBuilder = value[1].sql(user, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public Cmd getField() {
        return this.field;
    }

    @Override
    public Cmd[] getValue() {
        return this.value;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.field, this.value);
    }
}
