package db.sql.api.cmd.executor.method;

import db.sql.api.Getter;

public interface IUpdateMethod<SELF extends IUpdateMethod, TABLE, COLUMN, V> {

    SELF update(TABLE... tables);

    SELF update(Class... entities);

    SELF set(COLUMN field, V value);

    <T> SELF set(Getter<T> field, V value);

    /**
     * 实体类修改拦截
     *
     * @param entity
     */
    default void updateEntityIntercept(Class entity) {

    }
}
