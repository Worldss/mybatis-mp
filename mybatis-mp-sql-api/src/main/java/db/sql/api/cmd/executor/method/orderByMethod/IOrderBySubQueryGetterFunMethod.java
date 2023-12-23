package db.sql.api.cmd.executor.method.orderByMethod;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.basic.IOrderByDirection;
import db.sql.api.cmd.executor.ISubQuery;

import java.util.function.Function;

public interface IOrderBySubQueryGetterFunMethod<SELF extends IOrderBySubQueryGetterFunMethod, DATASET_FILED extends Cmd> extends IBaseOrderByMethods {

    default <T> SELF orderByWithFun(ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, Cmd> f) {
        return this.orderByWithFun(subQuery, defaultOrderByDirection(), column, f);
    }

    <T> SELF orderByWithFun(ISubQuery subQuery, IOrderByDirection orderByDirection, Getter<T> column, Function<DATASET_FILED, Cmd> f);

    default <T> SELF orderByWithFun(boolean when, ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderByWithFun(subQuery, defaultOrderByDirection(), column, f);
    }

    default <T> SELF orderByWithFun(boolean when, ISubQuery subQuery, IOrderByDirection orderByDirection, Getter<T> column, Function<DATASET_FILED, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderByWithFun(subQuery, orderByDirection, column, f);
    }
}
