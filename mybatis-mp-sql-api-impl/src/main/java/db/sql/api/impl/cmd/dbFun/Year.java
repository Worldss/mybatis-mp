package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Year extends BasicFunction<Year> {
    public Year(Cmd key) {
        super(SqlConst.YEAR, key);
    }
}
