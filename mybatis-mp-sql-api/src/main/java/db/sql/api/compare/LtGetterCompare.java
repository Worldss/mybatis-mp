package db.sql.api.compare;

import db.sql.api.Getter;

public interface LtGetterCompare<RV, V> {

    default <T> RV lt(Getter<T> column, V value) {
        return lt(column, value, true);
    }

    default <T> RV lt(Getter<T> column, V value, boolean when) {
        return this.lt(column, value, 1, when);
    }

    default <T> RV lt(Getter<T> column, V value, int storey) {
        return lt(column, value, storey, true);
    }

    <T> RV lt(Getter<T> column, V value, int storey, boolean when);

    default <T, T2> RV lt(Getter<T> column, Getter<T2> value) {
        return lt(column, value, true);
    }

    default <T, T2> RV lt(Getter<T> column, Getter<T2> value, boolean when) {
        return this.lt(column, 1, value, 1, when);
    }

    default <T, T2> RV lt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        return lt(column, columnStorey, value, valueStorey, true);
    }

    <T, T2> RV lt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when);
}