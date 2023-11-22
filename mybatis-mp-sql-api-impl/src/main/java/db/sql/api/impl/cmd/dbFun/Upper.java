package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Upper extends BasicFunction<Upper> {
    public Upper(Cmd value) {
        super(SqlConst.UPPER, value);
    }
}
