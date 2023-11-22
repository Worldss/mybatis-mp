package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Month extends BasicFunction<Month> {
    public Month(Cmd key) {
        super(SqlConst.MONTH, key);
    }
}
