package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.CmdJoins;
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
        sqlBuilder = sqlBuilder.append(SqlConst.ORDER_BY);
        sqlBuilder = CmdJoins.join(this, context, sqlBuilder, this.orderByValues, SqlConst.DELIMITER);
        return sqlBuilder;
    }
}
