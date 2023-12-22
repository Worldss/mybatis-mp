package db.sql.api.cmd.executor.method;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.ColumnField;
import db.sql.api.cmd.GetterColumnField;
import db.sql.api.cmd.basic.IOrderByDirection;
import db.sql.api.cmd.executor.ISubQuery;

import java.util.List;
import java.util.function.Function;

public interface IOrderByMethod<SELF extends IOrderByMethod, TABLE_FIELD, DATASET_FILED, COLUMN> {

    IOrderByDirection defaultOrderByDirection();

    default SELF orderBy(COLUMN column) {
        return this.orderBy(column, defaultOrderByDirection());
    }

    SELF orderBy(COLUMN column, IOrderByDirection orderByDirection);

    default SELF orderBy(COLUMN... columns) {
        return this.orderBy(defaultOrderByDirection(), columns);
    }

    default SELF orderBy(IOrderByDirection orderByDirection, COLUMN... columns) {
        for (COLUMN column : columns) {
            this.orderBy(column, orderByDirection);
        }
        return (SELF) this;
    }

    default SELF orderBy(List<COLUMN> columns) {
        return this.orderBy(defaultOrderByDirection(), columns);
    }

    default SELF orderBy(IOrderByDirection orderByDirection, List<COLUMN> columns) {
        for (COLUMN column : columns) {
            this.orderBy(column, orderByDirection);
        }
        return (SELF) this;
    }

    default <T> SELF orderBy(Getter<T> column) {
        return this.orderBy(column, defaultOrderByDirection());
    }

    default <T> SELF orderBy(Getter<T> column, IOrderByDirection orderByDirection) {
        return this.orderBy(column, 1, orderByDirection);
    }

    default <T> SELF orderBy(Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        return this.orderBy(column, null, f);
    }

    default <T> SELF orderBy(Getter<T> column, IOrderByDirection orderByDirection, Function<TABLE_FIELD, Cmd> f) {
        return this.orderBy(column, 1, orderByDirection, f);
    }

    default <T> SELF orderBy(Getter<T> column, int storey) {
        return this.orderBy(column, storey, defaultOrderByDirection());
    }

    default <T> SELF orderBy(Getter<T> column, int storey, IOrderByDirection orderByDirection) {
        return this.orderBy(column, storey, orderByDirection, null);
    }

    default <T> SELF orderBy(Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f) {
        return this.orderBy(column, storey, defaultOrderByDirection(), f);
    }

    <T> SELF orderBy(Getter<T> column, int storey, IOrderByDirection orderByDirection, Function<TABLE_FIELD, Cmd> f);

    default <T> SELF orderByFun(Function<TABLE_FIELD[], Cmd> f, Getter<T>... columns) {
        return this.orderByFun(null, f, columns);
    }

    default <T> SELF orderByFun(IOrderByDirection orderByDirection, Function<TABLE_FIELD[], Cmd> f, Getter<T>... columns) {
        return this.orderByFun(orderByDirection, f, 1, columns);
    }

    default <T> SELF orderByFun(Function<TABLE_FIELD[], Cmd> f, int storey, Getter<T>... columns) {
        return this.orderByFun(null, f, storey, columns);
    }

    <T> SELF orderByFun(IOrderByDirection orderByDirection, Function<TABLE_FIELD[], Cmd> f, int storey, Getter<T>... columns);

    default <T> SELF orderByFun(Function<TABLE_FIELD[], Cmd> f, GetterColumnField... getterColumnFields) {
        return this.orderByFun(null, f, getterColumnFields);
    }

    <T> SELF orderByFun(IOrderByDirection orderByDirection, Function<TABLE_FIELD[], Cmd> f, GetterColumnField... getterColumnFields);

    default <T> SELF orderBy(Getter<T>... columns) {
        return this.orderBy(defaultOrderByDirection(), columns);
    }

    default <T> SELF orderBy(IOrderByDirection orderByDirection, Getter<T>... columns) {
        return this.orderBy(1, orderByDirection, columns);
    }

    default <T> SELF orderBy(int storey, IOrderByDirection orderByDirection, Getter<T>... columns) {
        for (Getter<T> column : columns) {
            this.orderBy(column, storey, orderByDirection);
        }
        return (SELF) this;
    }

    default <T> SELF orderBy(ISubQuery subQuery, Getter<T> column) {
        return this.orderBy(subQuery, column, defaultOrderByDirection());
    }

    default <T> SELF orderBy(ISubQuery subQuery, Getter<T> column, IOrderByDirection orderByDirection) {
        return this.orderBy(subQuery, column, orderByDirection, null);
    }

    default <T> SELF orderBy(ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, Cmd> f) {
        return this.orderBy(subQuery, column, defaultOrderByDirection(), f);
    }

    <T> SELF orderBy(ISubQuery subQuery, Getter<T> column, IOrderByDirection orderByDirection, Function<DATASET_FILED, Cmd> f);

    default <T> SELF orderBy(ISubQuery subQuery, Getter<T>... columns) {
        return this.orderBy(subQuery, null, columns);
    }

    default <T> SELF orderBy(ISubQuery subQuery, IOrderByDirection orderByDirection, Getter<T>... columns) {
        for (Getter<T> column : columns) {
            this.orderBy(subQuery, column, orderByDirection);
        }
        return (SELF) this;
    }


    default SELF orderBy(ISubQuery subQuery, String columnName) {
        return this.orderBy(subQuery, columnName, defaultOrderByDirection());
    }

    default SELF orderBy(ISubQuery subQuery, String columnName, IOrderByDirection orderByDirection) {
        return this.orderBy(subQuery, columnName, orderByDirection, null);
    }

    default SELF orderBy(ISubQuery subQuery, String columnName, Function<DATASET_FILED, Cmd> f) {
        return this.orderBy(subQuery, columnName, defaultOrderByDirection(), f);
    }

    SELF orderBy(ISubQuery subQuery, String columnName, IOrderByDirection orderByDirection, Function<DATASET_FILED, Cmd> f);

    default <T> SELF orderByFun(ISubQuery subQuery, Function<DATASET_FILED[], Cmd> f, Getter<T>... columns) {
        return this.orderByFun(subQuery, defaultOrderByDirection(), f, columns);
    }

    <T> SELF orderByFun(ISubQuery subQuery, IOrderByDirection orderByDirection, Function<DATASET_FILED[], Cmd> f, Getter<T>... columns);

    default <T> SELF orderByFun(ISubQuery subQuery, Function<DATASET_FILED[], Cmd> f, ColumnField... columnFields) {
        return this.orderByFun(subQuery, defaultOrderByDirection(), f, columnFields);
    }

    <T> SELF orderByFun(ISubQuery subQuery, IOrderByDirection orderByDirection, Function<DATASET_FILED[], Cmd> f, ColumnField... columnFields);
}
