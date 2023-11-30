package db.sql.api.cmd.struct.delete;

import db.sql.api.Cmd;

public interface IDeleteTable<TABLE> extends Cmd {

    TABLE[] getTables();

}
