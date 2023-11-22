package db.sql.api.cmd.executor.method.compare;

import db.sql.api.cmd.LikeMode;

import java.io.Serializable;

/**
 * 比较器
 *
 * @param <RV>     返回
 * @param <COLUMN> 列
 * @param <V>      比较值
 */
public interface Compare<RV, COLUMN, V> extends
        EqGetterCompare<RV, V>,
        NeGetterCompare<RV, V>,
        GtGetterCompare<RV, V>,
        GteGetterCompare<RV, V>,
        LtGetterCompare<RV, V>,
        LteGetterCompare<RV, V>,
        LikeGetterCompare<RV>,
        NotLikeGetterCompare<RV>,
        BetweenGetterCompare<RV>,
        NotBetweenGetterCompare<RV>,
        IsNullGetterCompare<RV>,
        IsNotNullGetterCompare<RV>,
        EmptyGetterCompare<RV>,
        NotEmptyGetterCompare<RV>,
        Serializable {

    default RV empty(COLUMN column) {
        return this.empty(column, true);
    }

    RV empty(COLUMN column, boolean when);

    default RV notEmpty(COLUMN column) {
        return this.notEmpty(column, true);
    }

    RV notEmpty(COLUMN column, boolean when);

    default RV eq(COLUMN column, V value) {
        return eq(column, value, true);
    }

    RV eq(COLUMN column, V value, boolean when);

    default RV ne(COLUMN column, V value) {
        return ne(column, value, true);
    }

    RV ne(COLUMN column, V value, boolean when);

    default RV gt(COLUMN column, V value) {
        return gt(column, value, true);
    }

    RV gt(COLUMN column, V value, boolean when);

    default RV gte(COLUMN column, V value) {
        return gte(column, value, true);
    }

    RV gte(COLUMN column, V value, boolean when);

    default RV lt(COLUMN column, V value) {
        return lt(column, value, true);
    }

    RV lt(COLUMN column, V value, boolean when);

    default RV lte(COLUMN column, V value) {
        return lte(column, value, true);
    }

    RV lte(COLUMN column, V value, boolean when);


    default RV like(COLUMN column, String value) {
        return this.like(column, value, true);
    }

    default RV like(COLUMN column, String value, boolean when) {
        return this.like(column, value, LikeMode.DEFAULT, when);
    }

    default RV like(COLUMN column, String value, LikeMode mode) {
        return like(column, value, mode, true);
    }

    RV like(COLUMN column, String value, LikeMode mode, boolean when);

    default RV notLike(COLUMN column, String value) {
        return notLike(column, value, true);
    }

    default RV notLike(COLUMN column, String value, boolean when) {
        return notLike(column, value, LikeMode.DEFAULT, true);
    }

    default RV notLike(COLUMN column, String value, LikeMode mode) {
        return this.notLike(column, value, mode, true);
    }

    RV notLike(COLUMN column, String value, LikeMode mode, boolean when);

    default RV between(COLUMN column, Serializable value, Serializable value2) {
        return between(column, value, value2, true);
    }

    RV between(COLUMN column, Serializable value, Serializable value2, boolean when);

    default RV notBetween(COLUMN column, Serializable value, Serializable value2) {
        return notBetween(column, value, value2, true);
    }

    RV notBetween(COLUMN column, Serializable value, Serializable value2, boolean when);

    default RV isNull(COLUMN column) {
        return isNull(column, true);
    }

    RV isNull(COLUMN column, boolean when);

    default RV isNotNull(COLUMN column) {
        return isNotNull(column, true);
    }

    RV isNotNull(COLUMN column, boolean when);
}
