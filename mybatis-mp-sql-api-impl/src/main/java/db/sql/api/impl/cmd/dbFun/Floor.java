package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;


public class Floor extends BasicFunction<Floor> {
    public Floor(Cmd key) {
        super(SqlConst.FLOOR, key);
    }
}