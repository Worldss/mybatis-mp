package db.sql.api.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;

public class CountAll implements Cmd {

    public final static CountAll INSTANCE = new CountAll();

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return sqlBuilder.append(" COUNT(*) ");
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
