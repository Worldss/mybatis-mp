package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class FromUnixTime extends BasicFunction<Abs> {
    public FromUnixTime(Cmd key) {
        super(SqlConst.FROM_UNIXTIME, key);
    }
}
