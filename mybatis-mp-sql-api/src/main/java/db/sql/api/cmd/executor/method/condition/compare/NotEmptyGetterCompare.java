package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

public interface NotEmptyGetterCompare<RV> {

    default <T> RV notEmpty(Getter<T> column) {
        return notEmpty(column, true);
    }

    default <T> RV notEmpty(Getter<T> column, boolean when) {
        return this.notEmpty(column, 1, when);
    }

    default <T> RV notEmpty(Getter<T> column, int storey) {
        return notEmpty(column, storey, true);
    }

    <T> RV notEmpty(Getter<T> column, int storey, boolean when);
}
