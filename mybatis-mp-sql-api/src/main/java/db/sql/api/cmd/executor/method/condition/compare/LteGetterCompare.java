package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

public interface LteGetterCompare<RV, V> {

    default <T> RV lte(Getter<T> column, V value) {
        return lte(column, value, true);
    }

    default <T> RV lte(Getter<T> column, V value, boolean when) {
        return this.lte(column, value, 1, when);
    }

    default <T> RV lte(Getter<T> column, V value, int storey) {
        return lte(column, value, storey, true);
    }

    <T> RV lte(Getter<T> column, V value, int storey, boolean when);


    default <T, T2> RV lte(Getter<T> column, Getter<T2> value) {
        return lte(column, value, true);
    }

    default <T, T2> RV lte(Getter<T> column, Getter<T2> value, boolean when) {
        return this.lte(column, 1, value, 1, when);
    }

    default <T, T2> RV lte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        return lte(column, columnStorey, value, valueStorey, true);
    }

    <T, T2> RV lte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when);
}
