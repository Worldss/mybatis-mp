package db.sql.api.cmd.struct.insert;

import java.util.List;

public interface InsertValues<V> {

    List<List<V>> getValues();

}
