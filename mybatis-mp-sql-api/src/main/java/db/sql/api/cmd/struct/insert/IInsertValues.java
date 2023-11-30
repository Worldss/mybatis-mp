package db.sql.api.cmd.struct.insert;

import db.sql.api.Cmd;

import java.util.List;

public interface IInsertValues<V> extends Cmd {

    List<List<V>> getValues();

}
