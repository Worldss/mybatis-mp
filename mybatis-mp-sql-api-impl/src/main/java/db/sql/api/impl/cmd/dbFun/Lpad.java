package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.basic.BasicValue;
import db.sql.api.impl.tookit.SqlConst;

public class Lpad extends BasicFunction<Lpad> {

    private final int length;

    private final String pad;

    public Lpad(Cmd key, int length, String pad) {
        super(SqlConst.LPAD, key);
        this.length = length;
        this.pad = pad;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        this.key.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER).append(this.length);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
        sqlBuilder = new BasicValue(this.pad).sql(user, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }

}