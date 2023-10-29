package db.sql.api.compare;

import db.sql.api.Getter;

public interface IsNotNullGetterCompare<RV> {

    default <T> RV isNotNull(Getter<T> column) {
        return isNotNull(column, true);
    }

    default <T> RV isNotNull(Getter<T> column, boolean when) {
        return this.isNotNull(column, 1, when);
    }

    <T> RV isNotNull(Getter<T> column, int storey, boolean when);

}
