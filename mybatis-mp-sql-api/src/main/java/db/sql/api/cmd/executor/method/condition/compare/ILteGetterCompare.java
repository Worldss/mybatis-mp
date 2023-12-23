package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

public interface ILteGetterCompare<RV, V> {
    default <T> RV lte(Getter<T> column, V value) {
        return lte(true, column, 1, value);
    }

    default <T> RV lte(boolean when, Getter<T> column, V value) {
        return this.lte(when, column, 1, value);
    }

    default <T> RV lte(Getter<T> column, int storey, V value) {
        return lte(true, column, storey, value);
    }

    <T> RV lte(boolean when, Getter<T> column, int storey, V value);

    default <T, T2> RV lte(Getter<T> column, Getter<T2> value) {
        return lte(true, column, value);
    }

    default <T, T2> RV lte(boolean when, Getter<T> column, Getter<T2> value) {
        return this.lte(when, column, 1, value, 1);
    }

    default <T, T2> RV lte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        return lte(true, column, columnStorey, value, valueStorey);
    }

    <T, T2> RV lte(boolean when, Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey);
}
