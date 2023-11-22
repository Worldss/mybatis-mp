package db.sql.api.cmd.basic;

import db.sql.api.SqlBuilderContext;
import db.sql.api.Cmd;

public class CountAll implements Cmd {

    public final static CountAll INSTANCE = new CountAll();

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return sqlBuilder.append(" COUNT(*) ");
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
