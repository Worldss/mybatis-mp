package db.sql.api.cmd.executor.method.orderByMethod;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.basic.IOrderByDirection;

import java.util.function.Function;

public interface IOrderByGetterFunMethod<SELF extends IOrderByGetterFunMethod, TABLE_FIELD extends Cmd> extends IBaseOrderByMethods {

    default <T> SELF orderByWithFun(Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        return this.orderByWithFun(defaultOrderByDirection(), column, 1, f);
    }

    default <T> SELF orderByWithFun(Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f) {
        return this.orderByWithFun(defaultOrderByDirection(), column, storey, f);
    }

    default <T> SELF orderByWithFun(IOrderByDirection orderByDirection, Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        return this.orderByWithFun(orderByDirection, column, 1, f);
    }

    <T> SELF orderByWithFun(IOrderByDirection orderByDirection, Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f);

    default <T> SELF orderByWithFun(boolean when, Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderByWithFun(defaultOrderByDirection(), column, 1, f);
    }

    default <T> SELF orderByWithFun(boolean when, Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderByWithFun(defaultOrderByDirection(), column, storey, f);
    }

    default <T> SELF orderBy(boolean when, IOrderByDirection orderByDirection, Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderByWithFun(orderByDirection, column, 1, f);
    }


    default <T> SELF orderBy(boolean when, IOrderByDirection orderByDirection, Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderByWithFun(orderByDirection, column, storey, f);
    }
}
