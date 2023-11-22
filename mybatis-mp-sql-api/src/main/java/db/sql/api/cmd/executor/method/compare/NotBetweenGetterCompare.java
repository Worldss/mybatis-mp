package db.sql.api.cmd.executor.method.compare;

import db.sql.api.Getter;

import java.io.Serializable;

public interface NotBetweenGetterCompare<RV> {

    default <T> RV notBetween(Getter<T> column, Serializable value, Serializable value2) {
        return notBetween(column, value, value2, true);
    }

    default <T> RV notBetween(Getter<T> column, Serializable value, Serializable value2, boolean when) {
        return this.notBetween(column, value, value2, 1, when);
    }

    default <T> RV notBetween(Getter<T> column, Serializable value, Serializable value2, int storey) {
        return notBetween(column, value, value2, storey, true);
    }

    <T> RV notBetween(Getter<T> column, Serializable value, Serializable value2, int storey, boolean when);
}
