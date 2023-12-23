package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

import java.io.Serializable;

public interface IBetweenGetterCompare<RV> {

    default <T> RV between(Getter<T> column, Serializable value, Serializable value2) {
        return between(true, column, 1, value, value2);
    }

    default <T> RV between(boolean when, Getter<T> column, Serializable value, Serializable value2) {
        return this.between(when, column, 1, value, value2);
    }

    default <T> RV between(Getter<T> column, int storey, Serializable value, Serializable value2) {
        return between(true, column, storey, value, value2);
    }

    <T> RV between(boolean when, Getter<T> column, int storey, Serializable value, Serializable value2);
}
