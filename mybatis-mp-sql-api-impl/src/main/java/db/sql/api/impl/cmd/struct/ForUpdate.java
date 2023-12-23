package db.sql.api.impl.cmd.struct;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.struct.IForUpdate;
import db.sql.api.impl.tookit.SqlConst;

public class ForUpdate implements IForUpdate<ForUpdate> {

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(SqlConst.FOR_UPDATE);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return false;
    }
}
