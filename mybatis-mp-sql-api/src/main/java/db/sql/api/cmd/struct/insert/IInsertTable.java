package db.sql.api.cmd.struct.insert;

import db.sql.api.Cmd;

public interface IInsertTable<TABLE> extends Cmd {

    TABLE getTable();
}
