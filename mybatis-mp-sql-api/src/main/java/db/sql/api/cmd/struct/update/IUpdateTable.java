package db.sql.api.cmd.struct.update;

import db.sql.api.Cmd;

public interface IUpdateTable<TABLE> extends Cmd {

    TABLE[] getTables();

}
