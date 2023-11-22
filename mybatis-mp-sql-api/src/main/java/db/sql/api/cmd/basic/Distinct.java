package db.sql.api.cmd.basic;

import db.sql.api.SqlBuilderContext;
import db.sql.api.Cmd;

public class Distinct implements Cmd {
    public final static Distinct INSTANCE = new Distinct();

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return sqlBuilder.append(" DISTINCT ");
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
