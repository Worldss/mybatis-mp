package db.sql.api.cmd.executor.method.orderByMethod;


import db.sql.api.Cmd;
import db.sql.api.cmd.basic.IOrderByDirection;
import db.sql.api.cmd.executor.method.IOrderByMethod;

import java.util.List;

public interface IOrderByCmdMethod<SELF extends IOrderByMethod, COLUMN extends Cmd> extends IBaseOrderByMethods {

    default SELF orderBy(COLUMN column) {
        return this.orderBy(defaultOrderByDirection(), column);
    }

    SELF orderBy(IOrderByDirection orderByDirection, COLUMN column);

    default SELF orderBy(COLUMN... columns) {
        return this.orderBy(defaultOrderByDirection(), columns);
    }

    default SELF orderBy(IOrderByDirection orderByDirection, COLUMN... columns) {
        for (COLUMN column : columns) {
            this.orderBy(orderByDirection, column);
        }
        return (SELF) this;
    }

    default SELF orderBy(List<COLUMN> columns) {
        return this.orderBy(defaultOrderByDirection(), columns);
    }

    default SELF orderBy(IOrderByDirection orderByDirection, List<COLUMN> columns) {
        for (COLUMN column : columns) {
            this.orderBy(orderByDirection, column);
        }
        return (SELF) this;
    }
}
