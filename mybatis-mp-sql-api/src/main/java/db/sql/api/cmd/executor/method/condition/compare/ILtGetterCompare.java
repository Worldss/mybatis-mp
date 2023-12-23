package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

public interface ILtGetterCompare<RV, V> {

    default <T> RV lt(Getter<T> column, V value) {
        return lt(true, column, 1, value);
    }

    default <T> RV lt(boolean when, Getter<T> column, V value) {
        return this.lt(when, column, 1, value);
    }

    default <T> RV lt(Getter<T> column, int storey, V value) {
        return lt(true, column, storey, value);
    }

    <T> RV lt(boolean when, Getter<T> column, int storey, V value);

    default <T, T2> RV lt(Getter<T> column, Getter<T2> value) {
        return lt(true, column, value);
    }

    default <T, T2> RV lt(boolean when, Getter<T> column, Getter<T2> value) {
        return this.lt(when, column, 1, value, 1);
    }

    default <T, T2> RV lt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        return lt(true, column, columnStorey, value, valueStorey);
    }

    <T, T2> RV lt(boolean when, Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey);
}
