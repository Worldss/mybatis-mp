package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

import java.io.Serializable;

public interface INotBetweenGetterCompare<RV> {

    default <T> RV notBetween(Getter<T> column, Serializable value, Serializable value2) {
        return notBetween(true, column, 1, value, value2);
    }

    default <T> RV notBetween(boolean when, Getter<T> column, Serializable value, Serializable value2) {
        return this.notBetween(when, column, 1, value, value2);
    }

    default <T> RV notBetween(Getter<T> column, int storey, Serializable value, Serializable value2) {
        return notBetween(true, column, storey, value, value2);
    }

    <T> RV notBetween(boolean when, Getter<T> column, int storey, Serializable value, Serializable value2);
}
