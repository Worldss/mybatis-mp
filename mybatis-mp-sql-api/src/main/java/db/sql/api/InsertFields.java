package db.sql.api;

import java.util.List;

public interface InsertFields<COLUMN> {

    List<COLUMN> getFields();
}
