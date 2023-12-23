package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

public interface IEqGetterCompare<RV, V> {

    default <T> RV eq(Getter<T> column, V value) {
        return eq(true, column, 1, value);
    }

    default <T> RV eq(boolean when, Getter<T> column, V value) {
        return this.eq(when, column, 1, value);
    }

    default <T> RV eq(Getter<T> column, int storey, V value) {
        return eq(true, column, storey, value);
    }

    <T> RV eq(boolean when, Getter<T> column, int storey, V value);

    default <T, T2> RV eq(Getter<T> column, Getter<T2> value) {
        return eq(true, column, value);
    }

    default <T, T2> RV eq(boolean when, Getter<T> column, Getter<T2> value) {
        return this.eq(when, column, 1, value, 1);
    }

    default <T, T2> RV eq(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        return eq(true, column, columnStorey, value, valueStorey);
    }

    <T, T2> RV eq(boolean when, Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey);
}
