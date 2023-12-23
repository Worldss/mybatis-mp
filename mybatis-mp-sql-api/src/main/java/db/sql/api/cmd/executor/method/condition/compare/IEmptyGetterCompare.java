package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

public interface IEmptyGetterCompare<RV> {

    default <T> RV empty(Getter<T> column) {
        return empty(true, column, 1);
    }

    default <T> RV empty(boolean when, Getter<T> column) {
        return this.empty(when, column, 1);
    }

    default <T> RV empty(Getter<T> column, int storey) {
        return empty(true, column, storey);
    }

    <T> RV empty(boolean when, Getter<T> column, int storey);
}
