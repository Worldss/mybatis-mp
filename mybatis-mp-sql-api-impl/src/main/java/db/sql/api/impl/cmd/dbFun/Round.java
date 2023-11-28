package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.tookit.SqlConst;

import static db.sql.api.impl.tookit.SqlConst.ROUND;

public class Round extends BasicFunction<Round> {

    private final int precision;

    public Round(Cmd key, int precision) {
        super(ROUND, key);
        this.precision = precision;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        sqlBuilder = this.key.sql(module, this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER).append(this.precision);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(module, parent, context, sqlBuilder);
        return sqlBuilder;
    }

}
