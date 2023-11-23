package db.sql.api.cmd.struct;

import db.sql.api.Cmd;

import java.util.List;

public interface From<TABLE> extends Cmd {

    List<TABLE> getTables();
}
