package db.sql.api.cmd.struct.query;

import db.sql.api.Cmd;
import db.sql.api.cmd.basic.IOrderByDirection;

import java.util.List;

public interface IOrderBy<SELF extends IOrderBy> extends Cmd {

    SELF orderBy(IOrderByDirection orderByDirection, Cmd column);

    default SELF orderBy(IOrderByDirection orderByDirection, Cmd... columns) {
        for (Cmd column : columns) {
            this.orderBy(orderByDirection, column);
        }
        return (SELF) this;
    }


    default SELF orderBy(List<Cmd> columns, IOrderByDirection orderByDirection) {
        for (Cmd column : columns) {
            this.orderBy(orderByDirection, column);
        }
        return (SELF) this;
    }
}
