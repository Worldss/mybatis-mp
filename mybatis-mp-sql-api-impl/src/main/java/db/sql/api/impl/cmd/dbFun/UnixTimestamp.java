package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.tookit.SqlConst;

public class UnixTimestamp extends BasicFunction<UnixTimestamp> {

    public static final UnixTimestamp INSTANCE=new UnixTimestamp(null);

    public UnixTimestamp(Cmd key) {
        super(SqlConst.UNIX_TIMESTAMP, key);
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        if (this.key != null) {
            this.key.sql(this, context, sqlBuilder);
        }
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }
}
