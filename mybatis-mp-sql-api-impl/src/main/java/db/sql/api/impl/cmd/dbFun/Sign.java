package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Sign extends BasicFunction<Sign> {
    public Sign(Cmd key) {
        super(SqlConst.SIGN, key);
    }
}
