package db.sql.api.cmd.struct.update;

public interface UpdateSet<COLUMN, V> {

    COLUMN getField();

    V getValue();
}
