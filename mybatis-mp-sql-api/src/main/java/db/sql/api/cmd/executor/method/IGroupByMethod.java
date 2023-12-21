package db.sql.api.cmd.executor.method;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.executor.ISubQuery;

import java.util.List;
import java.util.function.Function;

public interface IGroupByMethod<SELF extends IGroupByMethod, TABLE_FIELD, DATASET_FIELD, COLUMN> {

    SELF groupBy(COLUMN column);


    default SELF groupBy(COLUMN... columns) {
        for (COLUMN column : columns) {
            this.groupBy(column);
        }
        return (SELF) this;
    }


    default SELF groupBy(List<COLUMN> columns) {
        for (COLUMN column : columns) {
            this.groupBy(column);
        }
        return (SELF) this;
    }

    default <T> SELF groupBy(Getter<T> column) {
        return this.groupBy(column, true);
    }

    default <T> SELF groupBy(Getter<T> column, boolean when) {
        return this.groupBy(column, 1, when);
    }

    default <T> SELF groupBy(Getter<T> column, int storey) {
        return this.groupBy(column, storey, true);
    }

    default <T> SELF groupBy(Getter<T> column, int storey, boolean when) {
        return this.groupBy(column, storey, when, null);
    }

    default <T> SELF groupBy(Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        return this.groupBy(column, true, f);
    }

    default <T> SELF groupBy(Getter<T> column, boolean when, Function<TABLE_FIELD, Cmd> f) {
        return this.groupBy(column, 1, when, f);
    }

    <T> SELF groupBy(Getter<T> column, int storey, boolean when, Function<TABLE_FIELD, Cmd> f);

    default <T> SELF groupBy(Getter<T>... columns) {
        return this.groupBy(true, columns);
    }

    default <T> SELF groupBy(int storey, Getter<T>... columns) {
        for (Getter<T> column : columns) {
            this.groupBy(column, storey);
        }
        return (SELF) this;
    }

    default <T> SELF groupBy(boolean when, Getter<T>... columns) {
        return this.groupBy(when, 1, columns);
    }

    default <T> SELF groupBy(boolean when, int storey, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupBy(storey, columns);
    }

    default <T> SELF groupByFun(Function<TABLE_FIELD[], Cmd> f, Getter<T>... columns) {
        return this.groupByFun(true, f, columns);
    }

    default <T> SELF groupByFun(boolean when, Function<TABLE_FIELD[], Cmd> f, Getter<T>... columns) {
        return this.groupByFun(when, f, 1, columns);
    }

    default <T> SELF groupByFun(Function<TABLE_FIELD[], Cmd> f, int storey, Getter<T>... columns) {
        return this.groupByFun(true, f, 1, columns);
    }

    <T> SELF groupByFun(boolean when, Function<TABLE_FIELD[], Cmd> f, int storey, Getter<T>... columns);

    default <T> SELF groupBy(ISubQuery subQuery, Getter<T> column) {
        return this.groupBy(subQuery, true, column);
    }

    default <T> SELF groupBy(ISubQuery subQuery, boolean when, Getter<T> column) {
        return this.groupBy(subQuery, when, column, null);
    }

    default <T> SELF groupBy(ISubQuery subQuery, Getter<T> column, Function<DATASET_FIELD, Cmd> f) {
        return this.groupBy(subQuery, true, column, f);
    }

    <T> SELF groupBy(ISubQuery subQuery, boolean when, Getter<T> column, Function<DATASET_FIELD, Cmd> f);

    default <T> SELF groupBy(ISubQuery subQuery, Getter<T>... columns) {
        for (Getter<T> column : columns) {
            this.groupBy(subQuery, column);
        }
        return (SELF) this;
    }

    default <T> SELF groupBy(ISubQuery subQuery, boolean when, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupBy(subQuery, columns);
    }

    default SELF groupBy(ISubQuery subQuery, String columnName) {
        return this.groupBy(subQuery, true, columnName);
    }

    default SELF groupBy(ISubQuery subQuery, boolean when, String columnName) {
        return this.groupBy(subQuery, when, columnName, null);
    }

    default SELF groupBy(ISubQuery subQuery, String columnName, Function<DATASET_FIELD, Cmd> f) {
        return this.groupBy(subQuery, true, columnName, f);
    }

    SELF groupBy(ISubQuery subQuery, boolean when, String columnName, Function<DATASET_FIELD, Cmd> f);

    default <T> SELF groupByFun(ISubQuery subQuery, Function<TABLE_FIELD[], Cmd> f, Getter<T>... columns) {
        return this.groupByFun(subQuery, true, f, columns);
    }

    default <T> SELF groupByFun(ISubQuery subQuery, boolean when, Function<TABLE_FIELD[], Cmd> f, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        for (Getter<T> column : columns) {
            this.groupByFun(subQuery, f, column);
        }
        return (SELF) this;
    }
}
