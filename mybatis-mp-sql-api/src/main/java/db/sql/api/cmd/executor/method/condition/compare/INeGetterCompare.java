package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;

public interface INeGetterCompare<RV, V> {

    default <T> RV ne(Getter<T> column, V value) {
        return ne(true, column, 1, value);
    }

    default <T> RV ne(boolean when, Getter<T> column, V value) {
        return this.ne(when, column, 1, value);
    }

    default <T> RV ne(Getter<T> column, int storey, V value) {
        return ne(true, column, storey, value);
    }

    <T> RV ne(boolean when, Getter<T> column, int storey, V value);

    default <T, T2> RV ne(Getter<T> column, Getter<T2> value) {
        return ne(true, column, value);
    }

    default <T, T2> RV ne(boolean when, Getter<T> column, Getter<T2> value) {
        return this.ne(when, column, 1, value, 1);
    }

    default <T, T2> RV ne(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        return ne(true, column, columnStorey, value, valueStorey);
    }

    <T, T2> RV ne(boolean when, Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey);
}
