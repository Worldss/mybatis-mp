package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Trim extends BasicFunction<Trim> {
    public Trim(Cmd key) {
        super(SqlConst.TRIM, key);
    }
}
