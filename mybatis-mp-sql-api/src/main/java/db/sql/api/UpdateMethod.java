package db.sql.api;

public interface UpdateMethod<SELF extends UpdateMethod, TABLE, COLUMN, V> {

    SELF update(TABLE... tables);

    SELF set(COLUMN field, V value);
}
