package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Acos extends BasicFunction<Acos> {
    public Acos(Cmd value) {
        super(SqlConst.ACOS, value);
    }
}
