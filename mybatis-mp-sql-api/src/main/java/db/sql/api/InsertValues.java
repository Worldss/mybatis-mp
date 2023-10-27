package db.sql.api;

import java.util.List;

public interface InsertValues<V> {

    List<List<V>> getValues();

}
