package db.sql.api.cmd.executor.method.compare;

import db.sql.api.Getter;
import db.sql.api.cmd.LikeMode;

public interface NotLikeGetterCompare<RV> {

    default <T> RV notLike(Getter<T> column, String value) {
        return notLike(column, value, true);
    }

    default <T> RV notLike(Getter<T> column, String value, boolean when) {
        return this.notLike(column, value, LikeMode.DEFAULT, when);
    }

    default <T> RV notLike(Getter<T> column, String value, int storey) {
        return notLike(column, value, storey, true);
    }

    default <T> RV notLike(Getter<T> column, String value, int storey, boolean when) {
        return this.notLike(column, value, LikeMode.DEFAULT, storey, when);
    }

    default <T> RV notLike(Getter<T> column, String value, LikeMode mode) {
        return notLike(column, value, mode, 1);
    }

    default <T> RV notLike(Getter<T> column, String value, LikeMode mode, boolean when) {
        return this.notLike(column, value, mode, 1, when);
    }

    default <T> RV notLike(Getter<T> column, String value, LikeMode mode, int storey) {
        return this.notLike(column, value, mode, storey, true);
    }

    <T> RV notLike(Getter<T> column, String value, LikeMode mode, int storey, boolean when);
}
