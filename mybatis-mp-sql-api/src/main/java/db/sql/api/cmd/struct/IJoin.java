package db.sql.api.cmd.struct;

import db.sql.api.Cmd;
import db.sql.api.cmd.JoinMode;

public interface IJoin<SELF extends IJoin, TABLE, ON> extends Cmd {

    TABLE getMainTable();

    TABLE getSecondTable();

    JoinMode getMode();

    ON getOn();

}
