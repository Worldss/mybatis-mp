package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.tookit.SqlConst;

public class Repeat extends BasicFunction<Repeat> {

    private final int n;

    public Repeat(Cmd key, int n) {
        super(SqlConst.REPEAT, key);
        this.n = n;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        sqlBuilder = this.key.sql(module, this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER).append(this.n);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(module, parent, context, sqlBuilder);
        return sqlBuilder;
    }

}