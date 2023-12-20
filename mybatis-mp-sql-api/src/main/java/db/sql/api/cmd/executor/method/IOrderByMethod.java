package db.sql.api.cmd.executor.method;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.executor.ISubQuery;

import java.util.List;
import java.util.function.Function;

public interface IOrderByMethod<SELF extends IOrderByMethod, TABLE_FIELD, DATASET_FILED, COLUMN> {

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

    default <T> SELF orderBy(Function<TABLE_FIELD[], Cmd> f, Getter<T>... columns) {
        return this.orderBy(true, f, columns);
    }

    default <T> SELF orderBy(boolean asc, Function<TABLE_FIELD[], Cmd> f, Getter<T>... columns) {
        return this.orderBy(asc, f, 1, columns);
    }

    default <T> SELF orderBy(Function<TABLE_FIELD[], Cmd> f, int storey, Getter<T>... columns) {
        return this.orderBy(true, f, storey, columns);
    }

    <T> SELF orderBy(boolean asc, Function<TABLE_FIELD[], Cmd> f, int storey, Getter<T>... columns);

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

    default <T> SELF orderBy(ISubQuery subQuery, Getter<T> column) {
        return this.orderBy(subQuery, column, true);
    }

    default <T> SELF orderBy(ISubQuery subQuery, Getter<T> column, boolean asc) {
        return this.orderBy(subQuery, column, asc, null);
    }

    default <T> SELF orderBy(ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, Cmd> f) {
        return this.orderBy(subQuery, column, true, f);
    }

    <T> SELF orderBy(ISubQuery subQuery, Getter<T> column, boolean asc, Function<DATASET_FILED, Cmd> f);

    default <T> SELF orderBy(ISubQuery subQuery, Getter<T>... columns) {
        return this.orderBy(subQuery, true, columns);
    }

    default <T> SELF orderBy(ISubQuery subQuery, boolean asc, Getter<T>... columns) {
        for (Getter<T> column : columns) {
            this.orderBy(subQuery, column, asc);
        }
        return (SELF) this;
    }


    default SELF orderBy(ISubQuery subQuery, String columnName) {
        return this.orderBy(subQuery, columnName, true);
    }

    default SELF orderBy(ISubQuery subQuery, String columnName, boolean asc) {
        return this.orderBy(subQuery, columnName, asc, null);
    }

    default SELF orderBy(ISubQuery subQuery, String columnName, Function<DATASET_FILED, Cmd> f) {
        return this.orderBy(subQuery, columnName, true, f);
    }

    SELF orderBy(ISubQuery subQuery, String columnName, boolean asc, Function<DATASET_FILED, Cmd> f);


    default <T> SELF orderBy(ISubQuery subQuery, Function<TABLE_FIELD[], Cmd> f, Getter<T>... columns) {
        return this.orderBy(subQuery, true, f, columns);
    }

    default <T> SELF orderBy(ISubQuery subQuery, boolean asc, Function<TABLE_FIELD[], Cmd> f, Getter<T>... columns) {
        return this.orderBy(subQuery, asc, f, 1, columns);
    }

    default <T> SELF orderBy(ISubQuery subQuery, Function<TABLE_FIELD[], Cmd> f, int storey, Getter<T>... columns) {
        return this.orderBy(subQuery, true, f, storey, columns);
    }

    <T> SELF orderBy(ISubQuery subQuery, boolean asc, Function<TABLE_FIELD[], Cmd> f, int storey, Getter<T>... columns);
}
