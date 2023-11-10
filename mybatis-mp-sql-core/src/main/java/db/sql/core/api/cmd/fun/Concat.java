package db.sql.core.api.cmd.fun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.cmd.BasicValue;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.tookit.SqlConst;

import java.io.Serializable;

import static db.sql.core.api.tookit.SqlConst.CONCAT;

public class Concat extends BasicFunction<Concat> {

    private final Cmd[] values;

    public Concat(Cmd key, Serializable... values) {
        super(CONCAT, key);
        Cmd[] vs = new Cmd[values.length];
        int i = 0;
        for (Serializable value : values) {
            if (value == null) {
                continue;
            }
            vs[i++] = new BasicValue(value);
        }
        this.values = vs;
    }

    public Concat(Cmd key, Cmd... values) {
        super(CONCAT, key);
        this.values = values;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(this.operator).append(SqlConst.BRACKET_LEFT);
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
