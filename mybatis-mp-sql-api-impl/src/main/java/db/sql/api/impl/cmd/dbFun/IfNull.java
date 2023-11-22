package db.sql.api.impl.cmd.dbFun;

import db.sql.api.SqlBuilderContext;
import db.sql.api.Cmd;
import db.sql.api.impl.cmd.basic.BasicValue;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.io.Serializable;

public class IfNull extends BasicFunction<IfNull> {

    private final Cmd value;

    public IfNull(Cmd key, Cmd value) {
        super(SqlConst.IFNULL, key);
        this.value = value;
    }

    public IfNull(Cmd key, Serializable value) {
        this(key,new BasicValue(value));
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        this.key.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER);
        this.value.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.key, this.value);
    }
}
