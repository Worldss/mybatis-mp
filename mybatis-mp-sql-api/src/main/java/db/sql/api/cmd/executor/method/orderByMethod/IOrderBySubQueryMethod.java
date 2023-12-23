package db.sql.api.cmd.executor.method.orderByMethod;


import db.sql.api.Cmd;
import db.sql.api.cmd.basic.IOrderByDirection;
import db.sql.api.cmd.executor.ISubQuery;

import java.util.function.Function;

public interface IOrderBySubQueryMethod<SELF extends IOrderBySubQueryMethod, DATASET_FILED extends Cmd> extends IBaseOrderByMethods {

    default SELF orderBy(ISubQuery subQuery, String columnName) {
        return this.orderBy(subQuery, defaultOrderByDirection(), columnName);
    }

    SELF orderBy(ISubQuery subQuery, IOrderByDirection orderByDirection, String columnName);

    default SELF orderByWithFun(ISubQuery subQuery, String columnName, Function<DATASET_FILED, Cmd> f) {
        return this.orderByWithFun(subQuery, defaultOrderByDirection(), columnName, f);
    }

    SELF orderByWithFun(ISubQuery subQuery, IOrderByDirection orderByDirection, String columnName, Function<DATASET_FILED, Cmd> f);


    default SELF orderBy(boolean when, ISubQuery subQuery, String columnName) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderBy(subQuery, defaultOrderByDirection(), columnName);
    }

    default SELF orderBy(boolean when, ISubQuery subQuery, IOrderByDirection orderByDirection, String columnName) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderBy(subQuery, orderByDirection, columnName);
    }

    default SELF orderByWithFun(boolean when, ISubQuery subQuery, String columnName, Function<DATASET_FILED, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderByWithFun(subQuery, defaultOrderByDirection(), columnName, f);
    }

    default SELF orderByWithFun(boolean when, ISubQuery subQuery, IOrderByDirection orderByDirection, String columnName, Function<DATASET_FILED, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderByWithFun(subQuery, orderByDirection, columnName, f);
    }
}
