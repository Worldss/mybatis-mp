package db.sql.api.compare;

import db.sql.api.Getter;

public interface NotBetweenGetterCompare<RV, V> {

    default <T> RV notBetween(Getter<T> column, V value, V value2) {
        return notBetween(column, value, value2, true);
    }

    default <T> RV notBetween(Getter<T> column, V value, V value2, boolean when) {
        return this.notBetween(column, value, value2, 1, when);
    }

    default <T> RV notBetween(Getter<T> column, V value, V value2, int storey) {
        return notBetween(column, value, value2, storey, true);
    }

    <T> RV notBetween(Getter<T> column, V value, V value2, int storey, boolean when);
}
