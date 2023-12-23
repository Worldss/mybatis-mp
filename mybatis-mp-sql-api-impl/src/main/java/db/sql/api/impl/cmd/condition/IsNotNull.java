package db.sql.api.impl.cmd.condition;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.basic.NULL;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

public class IsNotNull extends BaseCondition<Cmd, NULL> {

    private final Cmd field;

    public IsNotNull(Cmd field) {
        super(SqlConst.IS_NOT);
        this.field = field;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        field.sql(module, this, context, sqlBuilder);
        sqlBuilder.append(getOperator());
        NULL.NULL.sql(module, this, context, sqlBuilder);
        return sqlBuilder;
    }


    @Override
    public Cmd getField() {
        return field;
    }

    @Override
    public NULL getValue() {
        return NULL.NULL;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.field);
    }
}
