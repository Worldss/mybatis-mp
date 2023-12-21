package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.cmd.basic.Condition;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static db.sql.api.impl.tookit.SqlConst.CASE;

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
        return this.when(condition, Methods.convert(then));
    }

    public Case else_(Cmd then) {
        values.add(then);
        return this;
    }

    public Case else_(Serializable then) {
        return this.else_(Methods.convert(then));
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(SqlConst.BRACKET_LEFT).append(operator);
        for (Cmd item : values) {
            if (!(item instanceof CaseWhen)) {
                sqlBuilder.append(SqlConst.ELSE);
            }
            item.sql(module, this, context, sqlBuilder);
        }
        sqlBuilder.append(SqlConst.END);
        sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        appendAlias(module, parent, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.values);
    }
}

