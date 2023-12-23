package db.sql.api.impl.cmd.condition;


import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.tookit.CmdUtils;

public abstract class BasicCondition extends BaseCondition<Cmd, Cmd> {

    private final Cmd field;

    private final Cmd value;

    public BasicCondition(String operator, Cmd field, Cmd value) {
        super(operator);
        this.field = field;
        this.value = value;
    }

    @Override
    public Cmd getField() {
        return field;
    }

    @Override
    public Cmd getValue() {
        return value;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        field.sql(module, this, context, sqlBuilder);
        sqlBuilder.append(getOperator());
        value.sql(module, this, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.field, this.value);
    }
}
