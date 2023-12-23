package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

public interface IIsNullGetterCompare<RV> {

    default <T> RV isNull(Getter<T> column) {
        return isNull(true, column, 1);
    }

    default <T> RV isNull(boolean when, Getter<T> column) {
        return this.isNull(when, column, 1);
    }

    <T> RV isNull(boolean when, Getter<T> column, int storey);

}
