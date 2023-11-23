package db.sql.api.cmd.executor.method.compare;

import db.sql.api.Getter;

public interface GtGetterCompare<RV, V> {

    default <T> RV gt(Getter<T> column, V value) {
        return gt(column, value, true);
    }

    default <T> RV gt(Getter<T> column, V value, boolean when) {
        return this.gt(column, value, 1, when);
    }

    default <T> RV gt(Getter<T> column, V value, int storey) {
        return gt(column, value, storey, true);
    }

    <T> RV gt(Getter<T> column, V value, int storey, boolean when);

    default <T, T2> RV gt(Getter<T> column, Getter<T2> value) {
        return gt(column, value, true);
    }

    default <T, T2> RV gt(Getter<T> column, Getter<T2> value, boolean when) {
        return this.gt(column, 1, value, 1, when);
    }

    default <T, T2> RV gt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        return gt(column, columnStorey, value, valueStorey, true);
    }

    <T, T2> RV gt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when);
}
