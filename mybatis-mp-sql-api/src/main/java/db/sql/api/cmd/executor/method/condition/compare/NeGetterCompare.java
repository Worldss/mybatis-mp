package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

public interface NeGetterCompare<RV, V> {

    default <T> RV ne(Getter<T> column, V value) {
        return ne(column, value, true);
    }

    default <T> RV ne(Getter<T> column, V value, boolean when) {
        return this.ne(column, value, 1, when);
    }

    default <T> RV ne(Getter<T> column, V value, int storey) {
        return ne(column, value, storey, true);
    }

    <T> RV ne(Getter<T> column, V value, int storey, boolean when);


    default <T, T2> RV ne(Getter<T> column, Getter<T2> value) {
        return ne(column, value, true);
    }

    default <T, T2> RV ne(Getter<T> column, Getter<T2> value, boolean when) {
        return this.ne(column, 1, value, 1, when);
    }

    default <T, T2> RV ne(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        return ne(column, columnStorey, value, valueStorey, true);
    }

    <T, T2> RV ne(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when);
}
