package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

public interface INotEmptyGetterCompare<RV> {

    default <T> RV notEmpty(Getter<T> column) {
        return notEmpty(true, column, 1);
    }

    default <T> RV notEmpty(boolean when, Getter<T> column) {
        return this.notEmpty(when, column, 1);
    }

    default <T> RV notEmpty(Getter<T> column, int storey) {
        return notEmpty(true, column, storey);
    }

    <T> RV notEmpty(boolean when, Getter<T> column, int storey);
}
