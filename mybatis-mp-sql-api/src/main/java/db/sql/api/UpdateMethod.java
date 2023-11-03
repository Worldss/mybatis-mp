package db.sql.api;

public interface UpdateMethod<SELF extends UpdateMethod, TABLE, COLUMN, V> {

    @SuppressWarnings("unchecked")
    SELF update(TABLE... tables);

    SELF update(Class... entities);

    SELF set(COLUMN field, V value);

    <T> SELF set(Getter<T> field, V value);
}
