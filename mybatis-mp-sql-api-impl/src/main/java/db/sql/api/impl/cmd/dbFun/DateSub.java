package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.tookit.SqlConst;

import java.util.concurrent.TimeUnit;

public class DateSub extends BasicFunction<DateSub> {

    private final int n;

    private final TimeUnit timeUnit;

    public DateSub(Cmd key, int n, TimeUnit timeUnit) {
        super(SqlConst.DATE_SUB, key);
        this.n = n;
        this.timeUnit = timeUnit;
    }

    public static void main(String[] args) {
        System.out.println(TimeUnit.DAYS.name().substring(0, TimeUnit.DAYS.name().length() - 1));
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        this.key.sql(module, this, context, sqlBuilder);
        sqlBuilder.append(SqlConst.DELIMITER).append(SqlConst.INTERVAL).append(this.n);
        sqlBuilder.append(SqlConst.BLANK);
        sqlBuilder.append(timeUnit.name(), 0, timeUnit.name().length() - 1);
        sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        appendAlias(module, parent, context, sqlBuilder);
        return sqlBuilder;
    }
}