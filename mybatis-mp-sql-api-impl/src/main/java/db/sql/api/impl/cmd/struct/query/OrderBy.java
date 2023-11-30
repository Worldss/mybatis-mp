package db.sql.api.impl.cmd.struct.query;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.struct.query.IOrderBy;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderBy implements IOrderBy<OrderBy> {

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
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (this.orderByValues == null || this.orderByValues.isEmpty()) {
            return sqlBuilder;
        }
        sqlBuilder = sqlBuilder.append(SqlConst.ORDER_BY);
        sqlBuilder = CmdUtils.join(this, this, context, sqlBuilder, this.orderByValues, SqlConst.DELIMITER);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.orderByValues);
    }
}
