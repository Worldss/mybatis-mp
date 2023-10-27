package db.sql.api.compare;

import db.sql.api.Getter;
import db.sql.api.LikeMode;

public interface LikeGetterCompare<RV, V> {

    default <T> RV like(Getter<T> column, V value) {
        return like(column, value, true);
    }

    default <T> RV like(Getter<T> column, V value, boolean when) {
        return this.like(column, value, LikeMode.DEFAULT, when);
    }

    default <T> RV like(Getter<T> column, V value, int storey) {
        return like(column, value, storey, true);
    }

    default <T> RV like(Getter<T> column, V value, int storey, boolean when) {
        return this.like(column, value, LikeMode.DEFAULT, storey, when);
    }

    default <T> RV like(Getter<T> column, V value, LikeMode mode) {
        return like(column, value, mode, 1);
    }

    default <T> RV like(Getter<T> column, V value, LikeMode mode, boolean when) {
        return this.like(column, value, mode, 1, when);
    }

    default <T> RV like(Getter<T> column, V value, LikeMode mode, int storey) {
        return this.like(column, value, mode, storey, true);
    }

    <T> RV like(Getter<T> column, V value, LikeMode mode, int storey, boolean when);
}
