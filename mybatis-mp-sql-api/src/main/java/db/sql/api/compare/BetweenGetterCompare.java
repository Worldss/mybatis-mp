package db.sql.api.compare;

import db.sql.api.Getter;

public interface BetweenGetterCompare<RV, V> {

    default <T> RV between(Getter<T> column, V value, V value2) {
        return between(column, value, value2, true);
    }

    default <T> RV between(Getter<T> column, V value, V value2, boolean when) {
        return this.between(column, value, value2, 1, when);
    }

    default <T> RV between(Getter<T> column, V value, V value2, int storey) {
        return between(column, value, value2, storey, true);
    }

    <T> RV between(Getter<T> column, V value, V value2, int storey, boolean when);
}
