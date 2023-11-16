package db.sql.api.cmd.struct;

import db.sql.api.cmd.Cmd;
import db.sql.api.cmd.JoinMode;

public interface Join<SELF extends Join, TABLE, ON> extends Cmd {

    TABLE getMainTable();

    TABLE getSecondTable();

    JoinMode getMode();

    ON getOn();

}
