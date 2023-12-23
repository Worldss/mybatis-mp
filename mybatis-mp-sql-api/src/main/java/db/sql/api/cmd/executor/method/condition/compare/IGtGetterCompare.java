package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

public interface IGtGetterCompare<RV, V> {

    default <T> RV gt(Getter<T> column, V value) {
        return gt(true, column, 1, value);
    }

    default <T> RV gt(boolean when, Getter<T> column, V value) {
        return this.gt(when, column, 1, value);
    }

    default <T> RV gt(Getter<T> column, int storey, V value) {
        return gt(true, column, storey, value);
    }

    <T> RV gt(boolean when, Getter<T> column, int storey, V value);

    default <T, T2> RV gt(Getter<T> column, Getter<T2> value) {
        return gt(true, column, value);
    }

    default <T, T2> RV gt(boolean when, Getter<T> column, Getter<T2> value) {
        return this.gt(when, column, 1, value, 1);
    }

    default <T, T2> RV gt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        return gt(true, column, columnStorey, value, valueStorey);
    }

    <T, T2> RV gt(boolean when, Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey);
}
