package db.sql.api.cmd.executor.method.compare;

import db.sql.api.Getter;

public interface IsNullGetterCompare<RV> {

    default <T> RV isNull(Getter<T> column) {
        return isNull(column, true);
    }

    default <T> RV isNull(Getter<T> column, boolean when) {
        return this.isNull(column, 1, when);
    }

    <T> RV isNull(Getter<T> column, int storey, boolean when);

}
