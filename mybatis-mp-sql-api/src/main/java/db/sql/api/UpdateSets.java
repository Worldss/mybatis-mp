package db.sql.api;

import java.util.List;

public interface UpdateSets<COLUMN, V, UPDATE_SET_VALUE extends UpdateSet<COLUMN, V>> {

    List<UPDATE_SET_VALUE> getUpdateSets();


}
