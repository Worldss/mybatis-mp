package db.sql.api.cmd.executor.method.compare;

import db.sql.api.Getter;

public interface EqGetterCompare<RV, V> {

    default <T> RV eq(Getter<T> column, V value) {
        return eq(column, value, true);
    }

    default <T> RV eq(Getter<T> column, V value, boolean when) {
        return this.eq(column, value, 1, when);
    }

    default <T> RV eq(Getter<T> column, V value, int storey) {
        return eq(column, value, storey, true);
    }

    <T> RV eq(Getter<T> column, V value, int storey, boolean when);

    default <T, T2> RV eq(Getter<T> column, Getter<T2> value) {
        return eq(column, value, true);
    }

    default <T, T2> RV eq(Getter<T> column, Getter<T2> value, boolean when) {
        return this.eq(column, 1, value, 1, when);
    }

    default <T, T2> RV eq(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        return eq(column, columnStorey, value, valueStorey, true);
    }

    <T, T2> RV eq(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when);
}
