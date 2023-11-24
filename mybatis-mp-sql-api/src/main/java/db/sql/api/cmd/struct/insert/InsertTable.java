package db.sql.api.cmd.struct.insert;

import db.sql.api.Cmd;

public interface InsertTable<TABLE> extends Cmd {

    TABLE getTable();
}
