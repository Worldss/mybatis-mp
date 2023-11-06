package db.sql.core.api.cmd.fun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.cmd.BasicValue;
import db.sql.core.api.tookit.CmdJoins;
import db.sql.core.api.tookit.SqlConst;

import java.io.Serializable;

import static db.sql.core.api.tookit.SqlConst.CONCAT_WS;

public class ConcatAs extends BasicFunction<ConcatAs> {

    private final Cmd[] values;

    private final String split;

    public ConcatAs(Cmd key, String split, Serializable... values) {
        super(CONCAT_WS, key);
        Cmd[] vs = new Cmd[values.length];
        int i = 0;
        for (Serializable value : values) {
            if (value == null) {
                continue;
            }
            vs[i++] = new BasicValue(value);
        }
        this.split = split;
        this.values = vs;
    }

    public ConcatAs(Cmd key, String split, Cmd... values) {
        super(CONCAT_WS, key);
        this.split = split;
        this.values = values;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(this.operator).append(SqlConst.BRACKET_LEFT);
        sqlBuilder = sqlBuilder.append(SqlConst.SINGLE_QUOT(context.getDbType())).append(this.split).append(SqlConst.SINGLE_QUOT(context.getDbType())).append(SqlConst.DELIMITER);
        sqlBuilder = this.key.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
        sqlBuilder = CmdJoins.join(this, context, sqlBuilder, this.values, SqlConst.DELIMITER);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }
}
