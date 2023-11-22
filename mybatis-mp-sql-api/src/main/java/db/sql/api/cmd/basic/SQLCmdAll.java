package db.sql.api.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;

public class SQLCmdAll implements Cmd {

    public final static SQLCmdAll INSTANCE = new SQLCmdAll();

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(" * ");
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
