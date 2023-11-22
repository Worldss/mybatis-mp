package db.sql.api.impl.cmd.struct.query;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

public class OrderByValue implements Cmd {

    private final Cmd key;

    private final boolean asc;

    public OrderByValue(Cmd key, boolean asc) {
        this.key = key;
        this.asc = asc;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = key.sql(module, this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(asc ? SqlConst.ASC : SqlConst.DESC);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.key);
    }
}
