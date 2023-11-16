package db.sql.api.cmd.struct.insert;

import java.util.List;

public interface InsertFields<COLUMN> {

    List<COLUMN> getFields();
}
