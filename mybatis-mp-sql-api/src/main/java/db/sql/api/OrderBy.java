package db.sql.api;

import java.util.List;

public interface OrderBy<SELF extends OrderBy, COLUMN> {

    default SELF orderBy(COLUMN column) {
        return this.orderBy(column, true);
    }

    SELF orderBy(COLUMN column, boolean asc);

    @SuppressWarnings("unchecked")
    default SELF orderBy(COLUMN... columns) {
        return this.orderBy(true, columns);
    }

    @SuppressWarnings("unchecked")
    default SELF orderBy(boolean asc, COLUMN... columns) {
        for (COLUMN column : columns) {
            this.orderBy(column, asc);
        }
        return (SELF) this;
    }

    @SuppressWarnings("unchecked")
    default SELF orderBy(List<COLUMN> columns) {
        return this.orderBy(true, columns);
    }

    @SuppressWarnings("unchecked")
    default SELF orderBy(boolean asc, List<COLUMN> columns) {
        for (COLUMN column : columns) {
            this.orderBy(column, asc);
        }
        return (SELF) this;
    }

}
