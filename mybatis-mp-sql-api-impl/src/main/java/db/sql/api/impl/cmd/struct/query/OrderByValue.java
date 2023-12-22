package db.sql.api.impl.cmd.struct.query;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.basic.IOrderByDirection;
import db.sql.api.tookit.CmdUtils;

public class OrderByValue implements Cmd {

    private final Cmd key;

    private final IOrderByDirection orderByDirection;

    public OrderByValue(Cmd key, IOrderByDirection orderByDirection) {
        this.key = key;
        this.orderByDirection = orderByDirection;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        key.sql(module, this, context, sqlBuilder);
        orderByDirection.sql(module, this.key, context, sqlBuilder);
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.key);
    }
}
