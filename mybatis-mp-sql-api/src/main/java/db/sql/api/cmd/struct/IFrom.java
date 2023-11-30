package db.sql.api.cmd.struct;

import db.sql.api.Cmd;

import java.util.List;

public interface IFrom<TABLE> extends Cmd {

    List<TABLE> getTables();
}
