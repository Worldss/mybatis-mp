package db.sql.api.cmd.struct.delete;

import db.sql.api.Cmd;

public interface DeleteTable<TABLE> extends Cmd {

    TABLE[] getTables();

}
