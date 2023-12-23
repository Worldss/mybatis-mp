package db.sql.api.cmd.executor.method.condition;

import db.sql.api.cmd.LikeMode;
import db.sql.api.cmd.executor.method.condition.compare.*;

import java.io.Serializable;

/**
 * 比较器
 *
 * @param <RV>     返回
 * @param <COLUMN> 列
 * @param <V>      比较值
 */
public interface ICompare<RV, COLUMN, V> extends
        IEqGetterCompare<RV, V>,
        INeGetterCompare<RV, V>,
        IGtGetterCompare<RV, V>,
        IGteGetterCompare<RV, V>,
        ILtGetterCompare<RV, V>,
        ILteGetterCompare<RV, V>,
        ILikeGetterCompare<RV>,
        INotLikeGetterCompare<RV>,
        IBetweenGetterCompare<RV>,
        INotBetweenGetterCompare<RV>,
        IIsNullGetterCompare<RV>,
        IIsNotNullGetterCompare<RV>,
        IEmptyGetterCompare<RV>,
        INotEmptyGetterCompare<RV> {

    default RV empty(COLUMN column) {
        return this.empty(true, column);
    }

    RV empty(boolean when, COLUMN column);

    default RV notEmpty(COLUMN column) {
        return this.notEmpty(true, column);
    }

    RV notEmpty(boolean when, COLUMN column);

    default RV eq(COLUMN column, V value) {
        return eq(true, column, value);
    }

    RV eq(boolean when, COLUMN column, V value);

    default RV ne(COLUMN column, V value) {
        return ne(true, column, value);
    }

    RV ne(boolean when, COLUMN column, V value);

    default RV gt(COLUMN column, V value) {
        return gt(true, column, value);
    }

    RV gt(boolean when, COLUMN column, V value);

    default RV gte(COLUMN column, V value) {
        return gte(true, column, value);
    }

    RV gte(boolean when, COLUMN column, V value);

    default RV lt(COLUMN column, V value) {
        return lt(true, column, value);
    }

    RV lt(boolean when, COLUMN column, V value);

    default RV lte(COLUMN column, V value) {
        return lte(true, column, value);
    }

    RV lte(boolean when, COLUMN column, V value);


    default RV like(COLUMN column, String value) {
        return this.like(true, LikeMode.DEFAULT, column, value);
    }

    default RV like(boolean when, COLUMN column, String value) {
        return this.like(when, LikeMode.DEFAULT, column, value);
    }

    default RV like(LikeMode mode, COLUMN column, String value) {
        return like(true, mode, column, value);
    }

    RV like(boolean when, LikeMode mode, COLUMN column, String value);

    default RV notLike(COLUMN column, String value) {
        return notLike(true, LikeMode.DEFAULT, column, value);
    }

    default RV notLike(boolean when, COLUMN column, String value) {
        return notLike(when, LikeMode.DEFAULT, column, value);
    }

    default RV notLike(LikeMode mode, COLUMN column, String value) {
        return this.notLike(true, mode, column, value);
    }

    RV notLike(boolean when, LikeMode mode, COLUMN column, String value);

    default RV between(COLUMN column, Serializable value, Serializable value2) {
        return between(true, column, value, value2);
    }

    RV between(boolean when, COLUMN column, Serializable value, Serializable value2);

    default RV notBetween(COLUMN column, Serializable value, Serializable value2) {
        return notBetween(true, column, value, value2);
    }

    RV notBetween(boolean when, COLUMN column, Serializable value, Serializable value2);

    default RV isNull(COLUMN column) {
        return isNull(true, column);
    }

    RV isNull(boolean when, COLUMN column);

    default RV isNotNull(COLUMN column) {
        return isNotNull(true, column);
    }

    RV isNotNull(boolean when, COLUMN column);
}
