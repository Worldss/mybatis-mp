package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

import java.io.Serializable;

public interface IBetweenGetterCompare<RV> {

    default <T> RV between(Getter<T> column, Serializable value, Serializable value2) {
        return between(column, value, value2, true);
    }

    default <T> RV between(Getter<T> column, Serializable value, Serializable value2, boolean when) {
        return this.between(column, value, value2, 1, when);
    }

    default <T> RV between(Getter<T> column, Serializable value, Serializable value2, int storey) {
        return between(column, value, value2, storey, true);
    }

    <T> RV between(Getter<T> column, Serializable value, Serializable value2, int storey, boolean when);
}
