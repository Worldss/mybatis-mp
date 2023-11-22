package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.basic.Field;
import db.sql.api.impl.cmd.struct.query.Select;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.util.Objects;

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
        sqlBuilder = this.key.sql(this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.key);
    }
}
