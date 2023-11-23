package db.sql.api.cmd.struct.query;

import db.sql.api.Cmd;

import java.util.List;

public interface Select<SELF extends Select, COLUMN> extends Cmd {

    SELF distinct();

    boolean isDistinct();

    SELF select(COLUMN column);


    SELF select(COLUMN... columns);


    SELF select(List<COLUMN> columns);

    List<COLUMN> getSelectFiled();

}
