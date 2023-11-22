package db.sql.api.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;

public class Count1 implements Cmd {

    public final static Count1 INSTANCE = new Count1();

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return sqlBuilder.append(" COUNT(1) ");
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
