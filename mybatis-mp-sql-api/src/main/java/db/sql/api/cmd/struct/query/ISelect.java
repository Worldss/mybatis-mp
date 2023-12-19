package db.sql.api.cmd.struct.query;

import db.sql.api.Cmd;

import java.util.List;

public interface ISelect<SELF extends ISelect> extends Cmd {

    SELF distinct();

    boolean isDistinct();

    SELF select(Cmd column);

    SELF select(Cmd... columns);

    SELF select(List<Cmd> columns);

    List<Cmd> getSelectFiled();

    SELF selectIgnore(Cmd column);
}
