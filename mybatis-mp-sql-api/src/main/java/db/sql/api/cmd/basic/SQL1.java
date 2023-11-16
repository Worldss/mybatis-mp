package db.sql.api.cmd.basic;

import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.Cmd;

public class SQL1 implements Cmd {

    public final static SQL1 INSTANCE = new SQL1();

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(" 1 ");
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
