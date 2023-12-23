package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

public interface IIsNotNullGetterCompare<RV> {

    default <T> RV isNotNull(Getter<T> column) {
        return isNotNull(true, column, 1);
    }

    default <T> RV isNotNull(boolean when, Getter<T> column) {
        return this.isNotNull(when, column, 1);
    }

    <T> RV isNotNull(boolean when, Getter<T> column, int storey);

}
