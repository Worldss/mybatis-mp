package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.tookit.SqlConst;

import java.util.ArrayList;
import java.util.List;

public class OrderBy implements db.sql.api.OrderBy<OrderBy, Cmd>, Cmd {

    private final List<OrderByValue> orderByValues = new ArrayList<>();

    @Override
    public OrderBy orderBy(Cmd cmd, boolean asc) {
        orderByValues.add(new OrderByValue(cmd, asc));
        return this;
    }

    public List<OrderByValue> getOrderByFiled() {
        return orderByValues;
    }

    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (this.orderByValues == null || this.orderByValues.isEmpty()) {
            return sqlBuilder;
        }
        sqlBuilder = sqlBuilder.append(SqlConst.ORDER_BY);
        sqlBuilder = CmdUtils.join(this, context, sqlBuilder, this.orderByValues, SqlConst.DELIMITER);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.orderByValues);
    }
}
