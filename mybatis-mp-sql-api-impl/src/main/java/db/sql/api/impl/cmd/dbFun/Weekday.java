package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Weekday extends BasicFunction<Weekday> {
    public Weekday(Cmd key) {
        super(SqlConst.WEEKDAY, key);
    }
}
