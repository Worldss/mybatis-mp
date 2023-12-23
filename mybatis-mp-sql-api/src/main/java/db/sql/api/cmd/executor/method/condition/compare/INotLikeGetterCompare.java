package db.sql.api.cmd.executor.method.condition.compare;

import db.sql.api.Getter;
import db.sql.api.cmd.LikeMode;

public interface INotLikeGetterCompare<RV> {

    default <T> RV notLike(Getter<T> column, String value) {
        return notLike(true, LikeMode.DEFAULT, column, 1, value);
    }

    default <T> RV notLike(boolean when, Getter<T> column, String value) {
        return this.notLike(when, LikeMode.DEFAULT, column, 1, value);
    }

    default <T> RV notLike(Getter<T> column, String value, int storey) {
        return notLike(column, value, storey, true);
    }

    default <T> RV notLike(Getter<T> column, String value, int storey, boolean when) {
        return this.notLike(when, LikeMode.DEFAULT, column, storey, value);
    }

    default <T> RV notLike(Getter<T> column, String value, LikeMode mode) {
        return notLike(column, value, mode, 1);
    }

    default <T> RV notLike(Getter<T> column, String value, LikeMode mode, boolean when) {
        return this.notLike(when, mode, column, 1, value);
    }

    default <T> RV notLike(Getter<T> column, String value, LikeMode mode, int storey) {
        return this.notLike(true, mode, column, storey, value);
    }

    <T> RV notLike(boolean when, LikeMode mode, Getter<T> column, int storey, String value);
}
