package db.sql.api.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;

public class SQL1 implements Cmd {

    public final static SQL1 INSTANCE = new SQL1();

    @Override    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(" 1 ");
        return sqlBuilder;
    }

    @Override    public boolean contain(Cmd cmd) {
        return false;
    }
}
