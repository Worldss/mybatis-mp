package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.tookit.SqlConst;

public class CurrentDate extends BasicFunction<CurrentDate> {

    public CurrentDate() {
        super(null, null);
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(SqlConst.CURRENT_DATE);
        sqlBuilder.append(SqlConst.BRACKET_LEFT);
        sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        appendAlias(module, parent, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
