package db.sql.api;

public interface UpdateSet<COLUMN, V> {

    COLUMN getField();

    V getValue();
}
