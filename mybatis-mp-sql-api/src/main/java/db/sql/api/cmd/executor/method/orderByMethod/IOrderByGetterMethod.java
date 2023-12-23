package db.sql.api.cmd.executor.method.orderByMethod;


import db.sql.api.Getter;
import db.sql.api.cmd.basic.IOrderByDirection;

public interface IOrderByGetterMethod<SELF extends IOrderByGetterMethod> extends IBaseOrderByMethods {

    default <T> SELF orderBy(Getter<T> column) {
        return this.orderBy(defaultOrderByDirection(), column, 1);
    }

    default <T> SELF orderBy(Getter<T> column, int storey) {
        return this.orderBy(defaultOrderByDirection(), column, storey);
    }

    default <T> SELF orderBy(IOrderByDirection orderByDirection, Getter<T> column) {
        return this.orderBy(orderByDirection, column, 1);
    }

    <T> SELF orderBy(IOrderByDirection orderByDirection, Getter<T> column, int storey);

    default <T> SELF orderBy(boolean when, Getter<T> column) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderBy(defaultOrderByDirection(), column, 1);
    }

    default <T> SELF orderBy(boolean when, Getter<T> column, int storey) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderBy(defaultOrderByDirection(), column, storey);
    }

    default <T> SELF orderBy(boolean when, IOrderByDirection orderByDirection, Getter<T> column) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderBy(orderByDirection, column, 1);
    }


    default <T> SELF orderBy(boolean when, IOrderByDirection orderByDirection, Getter<T> column, int storey) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderBy(orderByDirection, column, storey);
    }
}
