package db.sql.api.cmd.struct.update;

import db.sql.api.Cmd;

public interface IUpdateSet<COLUMN, V> extends Cmd {

    COLUMN getField();

    V getValue();
}
