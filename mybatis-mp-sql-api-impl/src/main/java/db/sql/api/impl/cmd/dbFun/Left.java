package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.tookit.SqlConst;

public class Left extends BasicFunction<Left> {

    private final int length;

    public Left(Cmd key, int length) {
        super(SqlConst.LEFT, key);
        this.length = length;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        this.key.sql(module, this, context, sqlBuilder);
        sqlBuilder.append(SqlConst.DELIMITER).append(this.length);
        sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        appendAlias(module, parent, context, sqlBuilder);
        return sqlBuilder;
    }

}