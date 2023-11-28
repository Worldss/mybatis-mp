package db.sql.api.impl.cmd.struct.query;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.executor.SubQuery;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

public class With implements db.sql.api.cmd.struct.query.With<With> {

    private final SubQuery subQuery;

    public With(SubQuery subQuery) {
        this.subQuery = subQuery;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.WITH).append(this.subQuery.getAlias()).append(SqlConst.AS).append(SqlConst.BRACKET_LEFT);
        sqlBuilder =  this.subQuery.sql(module, this, context, sqlBuilder).append(SqlConst.BRACKET_RIGHT);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.subQuery);
    }
}
