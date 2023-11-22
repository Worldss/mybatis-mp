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

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder = sqlBuilder.append(operator).append(SqlConst.BRACKET_LEFT);
        sqlBuilder = this.key.sql(module, this, context, sqlBuilder);
        sqlBuilder = sqlBuilder.append(SqlConst.DELIMITER).append(SqlConst.INTERVAL).append(this.n);
        sqlBuilder = sqlBuilder.append(SqlConst.BLANK);
        sqlBuilder = sqlBuilder.append(timeUnit.name().substring(0, timeUnit.name().length() - 1));
        sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT);
        sqlBuilder = appendAlias(module, parent, sqlBuilder);
        return sqlBuilder;
    }

    public static void main(String[] args) {
        System.out.println(TimeUnit.DAYS.name().substring(0, TimeUnit.DAYS.name().length() - 1));
    }
}