package db.sql.api.impl.cmd.struct.query;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.executor.Query;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

public class Union implements db.sql.api.cmd.struct.query.Union {

    private final String operator;

    private final Query unionQuery;

    public Union(Query unionQuery) {
        this(SqlConst.UNION, unionQuery);
    }

    public Union(String operator, Query unionQuery) {
        this.operator = operator;
        this.unionQuery = unionQuery;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(this.operator);
        sqlBuilder = unionQuery.sql(module, this, context, sqlBuilder);
        return sqlBuilder;
    }


    public String getOperator() {
        return operator;
    }

    @Override
    public Query getUnionQuery() {
        return unionQuery;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.unionQuery);
    }
}
