package db.sql.api.cmd.basic;

import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.Cmd;

public class SQLCmdAll implements Cmd {

    public final static SQLCmdAll INSTANCE = new SQLCmdAll();

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(" * ");
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}