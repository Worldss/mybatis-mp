package db.sql.api.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;

public class Distinct implements Cmd {
    public final static Distinct INSTANCE = new Distinct();

    @Override    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return sqlBuilder.append(" DISTINCT ");
    }

    @Override    public boolean contain(Cmd cmd) {
        return false;
    }
}
