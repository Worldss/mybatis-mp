package db.sql.core.api.cmd.fun;

import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.Cmd;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.cmd.basic.BasicValue;
import db.sql.core.api.cmd.basic.Condition;
import db.sql.core.api.tookit.SqlConst;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static db.sql.core.api.tookit.SqlConst.CASE;

public class Case extends BasicFunction<Case> {

    private final List<Cmd> values = new ArrayList<>();

    public Case() {
        super(CASE, null);
    }

    public Case when(Condition condition, Cmd then) {
        values.add(new CaseWhen(condition, then));
        return this;
    }

    public Case when(Condition condition, Serializable then) {
        return this.when(condition, new BasicValue(then));
    }

    public Case else_(Cmd then) {
        values.add(then);
        return this;
    }

    public Case else_(Serializable then) {
        return this.else_(new BasicValue(then));
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_LEFT).append(operator);
        for (Cmd item : values) {
            if (!(item instanceof CaseWhen)) {
                sqlBuilder = sqlBuilder.append(SqlConst.ELSE);
            }
            item.sql(this, context, sqlBuilder);
        }
        sqlBuilder = sqlBuilder.append(SqlConst.END);
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(user, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.values);
    }
}

