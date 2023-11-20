package db.sql.api.cmd.executor.method.compare;

import db.sql.api.Getter;

public interface EmptyGetterCompare<RV> {

    default <T> RV empty(Getter<T> column) {
        return empty(column, true);
    }

    default <T> RV empty(Getter<T> column, boolean when) {
        return this.empty(column, 1, when);
    }

    default <T> RV empty(Getter<T> column, int storey) {
        return empty(column, storey, true);
    }

    <T> RV empty(Getter<T> column, int storey, boolean when);
}
