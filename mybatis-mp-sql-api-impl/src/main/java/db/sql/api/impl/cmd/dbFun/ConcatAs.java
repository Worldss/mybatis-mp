package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.io.Serializable;

import static db.sql.api.impl.tookit.SqlConst.CONCAT;
import static db.sql.api.impl.tookit.SqlConst.CONCAT_WS;

public class ConcatAs extends BasicFunction<ConcatAs> {

    private final Cmd[] values;

    private final Cmd split;

    public ConcatAs(Cmd key, String split, Serializable... values) {
        super(CONCAT_WS, key);
        Cmd[] vs = new Cmd[values.length];
        int i = 0;
        for (Serializable value : values) {
            if (value == null) {
                continue;
            }
            vs[i++] = Methods.convert(value);
        }
        this.split = Methods.convert(split);
        this.values = vs;
    }

    public ConcatAs(Cmd key, String split, Cmd... values) {
        super(CONCAT_WS, key);
        this.split = Methods.convert(split);
        this.values = values;
    }

    public ConcatAs(Cmd key, String split, Object... values) {
        super(CONCAT, key);
        this.split = Methods.convert(split);
        Cmd[] vs = new Cmd[values.length];
        int i = 0;
        for (Object value : values) {
            if (value == null) {
                continue;
            }
            if (value instanceof Cmd) {
                vs[i++] = (Cmd) value;
            } else {
                vs[i++] = Methods.convert(value);
            }
        }
        this.values = vs;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(this.operator).append(SqlConst.BRACKET_LEFT);
        sqlBuilder = this.split.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
        sqlBuilder = this.key.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
        sqlBuilder = CmdUtils.join(this, context, sqlBuilder, this.values, SqlConst.DELIMITER);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.key, this.values);
    }
}
