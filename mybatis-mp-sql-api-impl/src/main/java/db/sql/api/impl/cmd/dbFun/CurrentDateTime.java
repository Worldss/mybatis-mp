package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.tookit.SqlConst;

public class CurrentDateTime extends BasicFunction<CurrentDateTime> {

    public CurrentDateTime() {
        super(null, null);
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.CURRENT_DATE_TIME(context.getDbType()));
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_LEFT);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
