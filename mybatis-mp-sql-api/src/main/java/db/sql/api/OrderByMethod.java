package db.sql.api;

import java.util.List;
import java.util.function.Function;

public interface OrderByMethod<SELF extends OrderByMethod, TABLE_FIELD, COLUMN> {

    default SELF orderBy(COLUMN column) {
        return this.orderBy(column, true);
    }

    SELF orderBy(COLUMN column, boolean asc);


    default SELF orderBy(COLUMN... columns) {
        return this.orderBy(true, columns);
    }


    default SELF orderBy(boolean asc, COLUMN... columns) {
        for (COLUMN column : columns) {
            this.orderBy(column, asc);
        }
        return (SELF) this;
    }


    default SELF orderBy(List<COLUMN> columns) {
        return this.orderBy(true, columns);
    }


    default SELF orderBy(boolean asc, List<COLUMN> columns) {
        for (COLUMN column : columns) {
            this.orderBy(column, asc);
        }
        return (SELF) this;
    }

    default <T> SELF orderBy(Getter<T> column) {
        return this.orderBy(column, true);
    }

    default <T> SELF orderBy(Getter<T> column, boolean asc) {
        return this.orderBy(column, 1, asc);
    }

    default <T> SELF orderBy(Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        return this.orderBy(column, true, f);
    }

    default <T> SELF orderBy(Getter<T> column, boolean asc, Function<TABLE_FIELD, Cmd> f) {
        return this.orderBy(column, 1, asc, f);
    }

    default <T> SELF orderBy(Getter<T> column, int storey) {
        return this.orderBy(column, storey, null);
    }

    default <T> SELF orderBy(Getter<T> column, int storey, boolean asc) {
        return this.orderBy(column, storey, asc, null);
    }

    default <T> SELF orderBy(Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f) {
        return this.orderBy(column, storey, true, f);
    }

    <T> SELF orderBy(Getter<T> column, int storey, boolean asc, Function<TABLE_FIELD, Cmd> f);


    default <T> SELF orderBy(Getter<T>... columns) {
        return this.orderBy(true, columns);
    }


    default <T> SELF orderBy(boolean asc, Getter<T>... columns) {
        return this.orderBy(1, asc, columns);
    }


    default <T> SELF orderBy(int storey, boolean asc, Getter<T>... columns) {
        for (Getter<T> column : columns) {
            this.orderBy(column, storey, asc);
        }
        return (SELF) this;
    }
}
