package db.sql.api.cmd.basic;

import db.sql.api.Cmd;

public interface IColumn<T extends IColumn<T>> extends Alias<T>,Cmd {
}
