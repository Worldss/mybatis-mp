package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Hour extends BasicFunction<Hour> {
    public Hour(Cmd key) {
        super(SqlConst.HOUR, key);
    }
}
