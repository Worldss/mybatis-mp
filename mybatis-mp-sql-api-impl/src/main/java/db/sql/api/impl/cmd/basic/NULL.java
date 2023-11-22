package db.sql.api.impl.cmd.basic;

import db.sql.api.SqlBuilderContext;
import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class NULL implements Value {

    public static final NULL NULL = new NULL();

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return sqlBuilder.append(SqlConst.NULL);
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
