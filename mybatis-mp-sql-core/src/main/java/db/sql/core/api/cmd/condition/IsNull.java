package db.sql.core.api.cmd.condition;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.cmd.NULL;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.tookit.SqlConst;

public class IsNull extends BaseCondition<Cmd, NULL> {

    private final Cmd field;

    public IsNull(Cmd field) {
        super(SqlConst.IS);
        this.field = field;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = field.sql(user, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(getOperator());
        sqlBuilder = NULL.NULL.sql(user, context, sqlBuilder);
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
