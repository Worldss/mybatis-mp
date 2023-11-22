package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Lower extends BasicFunction<Lower> {
    public Lower(Cmd key) {
        super(SqlConst.LOWER, key);
    }
}
