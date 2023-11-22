package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Radians extends BasicFunction<Radians> {
    public Radians(Cmd key) {
        super(SqlConst.RADIANS, key);
    }
}
