package db.sql.api.cmd.executor.method;

import db.sql.api.Getter;

public interface UpdateMethod<SELF extends UpdateMethod, TABLE, COLUMN, V> {

    SELF update(TABLE... tables);

    SELF update(Class... entities);

    SELF set(COLUMN field, V value);

    <T> SELF set(Getter<T> field, V value);
}
