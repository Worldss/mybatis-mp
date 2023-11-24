package db.sql.api.cmd.struct.update;

import db.sql.api.Cmd;

public interface UpdateTable<TABLE> extends Cmd {

    TABLE[] getTables();

}
