package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Min extends BasicFunction<Min> {
    public Min(Cmd key) {
        super(SqlConst.MIN, key);
    }
}
