package db.sql.api.cmd.executor.method.orderByMethod;


import db.sql.api.Getter;
import db.sql.api.cmd.basic.IOrderByDirection;
import db.sql.api.cmd.executor.method.IOrderByMethod;

public interface IOrderByMultiGetterMethod<SELF extends IOrderByMethod> extends IBaseOrderByMethods {

    default <T> SELF orderBy(Getter<T>... columns) {
        return this.orderBy(defaultOrderByDirection(), 1, columns);
    }

    default <T> SELF orderBy(int storey, Getter<T>... columns) {
        return this.orderBy(defaultOrderByDirection(), storey, columns);
    }

    default <T> SELF orderBy(IOrderByDirection orderByDirection, Getter<T>... columns) {
        return this.orderBy(orderByDirection, 1, columns);
    }

    <T> SELF orderBy(IOrderByDirection orderByDirection, int storey, Getter<T>... columns);

    default <T> SELF orderBy(boolean when, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderBy(defaultOrderByDirection(), 1, columns);
    }

    default <T> SELF orderBy(boolean when, int storey, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderBy(defaultOrderByDirection(), storey, columns);
    }

    default <T> SELF orderBy(boolean when, IOrderByDirection orderByDirection, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderBy(orderByDirection, 1, columns);
    }

    default <T> SELF orderBy(boolean when, IOrderByDirection orderByDirection, int storey, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.orderBy(orderByDirection, storey, columns);
    }
}
