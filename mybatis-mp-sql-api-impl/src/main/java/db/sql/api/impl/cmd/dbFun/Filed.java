package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.basic.BasicValue;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.io.Serializable;

public class Filed extends BasicFunction<Filed> {

    private final Cmd[] values;

    public Filed(Cmd key, Serializable... values) {
        super(SqlConst.FILED, key);
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

    public Filed(Cmd key, Cmd... values) {
        super(SqlConst.FILED, key);
        this.values = values;
    }

    public Filed(Cmd key, Object... values) {
        super(SqlConst.FILED, key);
        Cmd[] vs = new Cmd[values.length];
        int i = 0;
        for (Object value : values) {
            if (value == null) {
                continue;
            }
            if(value instanceof Cmd){
                vs[i++]=(Cmd)value;
            }else{
                vs[i++] = new BasicValue(value);
            }
        }
        this.values = vs;
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
