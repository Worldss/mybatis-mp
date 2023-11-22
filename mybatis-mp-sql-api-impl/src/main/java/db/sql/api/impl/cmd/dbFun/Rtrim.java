package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Rtrim extends BasicFunction<Rtrim> {
    public Rtrim(Cmd key) {
        super(SqlConst.RTRIM, key);
    }
}
