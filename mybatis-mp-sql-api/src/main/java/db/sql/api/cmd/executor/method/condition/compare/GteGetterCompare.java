package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

public interface GteGetterCompare<RV, V> {

    default <T> RV gte(Getter<T> column, V value) {
        return gte(column, value, true);
    }

    default <T> RV gte(Getter<T> column, V value, boolean when) {
        return this.gte(column, value, 1, when);
    }

    default <T> RV gte(Getter<T> column, V value, int storey) {
        return gte(column, value, storey, true);
    }

    <T> RV gte(Getter<T> column, V value, int storey, boolean when);

    default <T, T2> RV gte(Getter<T> column, Getter<T2> value) {
        return gte(column, value, true);
    }

    default <T, T2> RV gte(Getter<T> column, Getter<T2> value, boolean when) {
        return this.gte(column, 1, value, 1, when);
    }

    default <T, T2> RV gte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        return gte(column, columnStorey, value, valueStorey, true);
    }

    <T, T2> RV gte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when);
}
