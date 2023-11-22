package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Degrees extends BasicFunction<Degrees> {
    public Degrees(Cmd key) {
        super(SqlConst.DEGREES, key);
    }
}
