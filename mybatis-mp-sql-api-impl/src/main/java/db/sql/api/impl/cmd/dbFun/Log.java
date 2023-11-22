package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Log extends BasicFunction<Log> {
    public Log(Cmd value) {
        super(SqlConst.LOG, value);
    }
}
