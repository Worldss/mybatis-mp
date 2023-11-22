package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Day extends BasicFunction<Day> {
    public Day(Cmd key) {
        super(SqlConst.DAY, key);
    }
}
