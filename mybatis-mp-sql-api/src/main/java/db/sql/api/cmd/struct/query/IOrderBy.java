package db.sql.api.cmd.struct.query;

import db.sql.api.Cmd;

import java.util.List;

public interface IOrderBy<SELF extends IOrderBy> extends Cmd {

    default SELF orderBy(Cmd column) {
        return this.orderBy(column, true);
    }

    SELF orderBy(Cmd column, boolean asc);

    default SELF orderBy(Cmd... columns) {
        return this.orderBy(true, columns);
    }

    default SELF orderBy(boolean asc, Cmd... columns) {
        for (Cmd column : columns) {
            this.orderBy(column, asc);
        }
        return (SELF) this;
    }

    default SELF orderBy(List<Cmd> columns) {
        return this.orderBy(true, columns);
    }

    default SELF orderBy(boolean asc, List<Cmd> columns) {
        for (Cmd column : columns) {
            this.orderBy(column, asc);
        }
        return (SELF) this;
    }
}
