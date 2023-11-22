package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Sum extends BasicFunction<Sum> {

    public Sum(Cmd key) {
        super(SqlConst.SUM, key);
    }
}
