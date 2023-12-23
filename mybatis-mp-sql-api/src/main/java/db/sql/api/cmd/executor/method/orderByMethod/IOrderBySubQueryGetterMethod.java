package db.sql.api.cmd.executor.method.orderByMethod;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.basic.IOrderByDirection;
import db.sql.api.cmd.executor.ISubQuery;

import java.util.function.Function;

public interface IOrderBySubQueryGetterMethod<SELF extends IOrderBySubQueryGetterMethod, DATASET_FILED extends Cmd> extends IBaseOrderByMethods {

    default <T> SELF orderBy(ISubQuery subQuery, Getter<T> column) {
        return this.orderBy(subQuery, defaultOrderByDirection(), column);
    }

    <T> SELF orderBy(ISubQuery subQuery, IOrderByDirection orderByDirection, Getter<T> column);

    default <T> SELF orderBy(ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, Cmd> f) {
        return this.orderBy(subQuery, defaultOrderByDirection(), column, f);
    }

    <T> SELF orderBy(ISubQuery subQuery, IOrderByDirection orderByDirection, Getter<T> column, Function<DATASET_FILED, Cmd> f);


    default <T> SELF orderBy(boolean when, ISubQuery subQuery, Getter<T> column) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderBy(subQuery, defaultOrderByDirection(), column);
    }

    default <T> SELF orderBy(boolean when, ISubQuery subQuery, IOrderByDirection orderByDirection, Getter<T> column) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderBy(subQuery, defaultOrderByDirection(), column);
    }

    default <T> SELF orderBy(boolean when, ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderBy(subQuery, defaultOrderByDirection(), column, f);
    }

    default <T> SELF orderBy(boolean when, ISubQuery subQuery, IOrderByDirection orderByDirection, Getter<T> column, Function<DATASET_FILED, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderBy(subQuery, orderByDirection, column, f);
    }
}
