package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.tookit.SqlConst;

public class UnixTimestamp extends BasicFunction<UnixTimestamp> {

    public UnixTimestamp() {
        this(null);
    }

    public UnixTimestamp(Cmd key) {
        super(SqlConst.UNIX_TIMESTAMP, key);
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        if (this.key != null) {
            this.key.sql(module, this, context, sqlBuilder);
        }
        sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        appendAlias(module, parent, context, sqlBuilder);
        return sqlBuilder;
    }
}
