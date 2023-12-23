package db.sql.api.impl.cmd.condition;


import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

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
        this(key, Methods.convert(value1), Methods.convert(value2));
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        field.sql(module, this, context, sqlBuilder);
        sqlBuilder.append(getOperator());
        value[0].sql(module, this, context, sqlBuilder);
        sqlBuilder.append(SqlConst.AND);
        value[1].sql(module, this, context, sqlBuilder);
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
