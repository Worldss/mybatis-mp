package db.sql.core.api.cmd.fun;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.tookit.SqlConst;


public class Floor extends BasicFunction<Floor> {
    public Floor(Cmd value) {
        super(SqlConst.FLOOR, value);
    }
}