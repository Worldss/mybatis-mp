package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;
import db.sql.api.cmd.LikeMode;

public interface ILikeGetterCompare<RV> {

    default <T> RV like(Getter<T> column, String value) {
        return like(column, value, true);
    }

    default <T> RV like(Getter<T> column, String value, boolean when) {
        return this.like(column, value, LikeMode.DEFAULT, when);
    }

    default <T> RV like(Getter<T> column, String value, int storey) {
        return like(column, value, storey, true);
    }

    default <T> RV like(Getter<T> column, String value, int storey, boolean when) {
        return this.like(column, value, LikeMode.DEFAULT, storey, when);
    }

    default <T> RV like(Getter<T> column, String value, LikeMode mode) {
        return like(column, value, mode, 1);
    }

    default <T> RV like(Getter<T> column, String value, LikeMode mode, boolean when) {
        return this.like(column, value, mode, 1, when);
    }

    default <T> RV like(Getter<T> column, String value, LikeMode mode, int storey) {
        return this.like(column, value, mode, storey, true);
    }

    <T> RV like(Getter<T> column, String value, LikeMode mode, int storey, boolean when);
}
