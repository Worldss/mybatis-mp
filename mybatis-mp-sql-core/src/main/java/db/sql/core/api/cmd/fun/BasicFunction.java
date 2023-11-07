package db.sql.core.api.cmd.fun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.cmd.Field;
import db.sql.core.api.cmd.Select;
import db.sql.core.api.tookit.CmdUtils;
import db.sql.core.api.tookit.SqlConst;

public abstract class BasicFunction<T extends BasicFunction> extends Field<BasicFunction<T>> implements Function, FunctionInterface {

    protected final String operator;

    protected final Cmd key;

    public BasicFunction(String operator, Cmd key) {
        this.operator = operator;
        this.key = key;
    }

    protected StringBuilder appendAlias(Cmd user, StringBuilder sqlBuilder) {
        //拼接 select 的别名
        if (user instanceof Select) {
            if (this.getAlias() != null) {
                sqlBuilder = sqlBuilder.append(SqlConst.AS);
                sqlBuilder = sqlBuilder.append(this.getAlias());
            }
            return sqlBuilder;
        }

        return sqlBuilder;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        this.key.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.key);
    }
}
