package db.sql.api.cmd.struct.insert;

import db.sql.api.Cmd;

import java.util.List;

public interface InsertFields<COLUMN> extends Cmd {

    List<COLUMN> getFields();
}
