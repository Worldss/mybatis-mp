package db.sql.api.cmd.basic;

import db.sql.api.Cmd;

public interface Dataset <T extends Dataset,FIELD> extends Cmd, Alias<T> {

    FIELD $(String name);
}
