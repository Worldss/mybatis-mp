package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Log extends BasicFunction<Log> {
    public Log(Cmd key) {
        super(SqlConst.LOG, key);
    }
}
