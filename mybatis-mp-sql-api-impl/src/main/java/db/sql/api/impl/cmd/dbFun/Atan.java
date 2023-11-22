package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Atan extends BasicFunction<Atan> {
    public Atan(Cmd key) {
        super(SqlConst.ATAN, key);
    }
}
