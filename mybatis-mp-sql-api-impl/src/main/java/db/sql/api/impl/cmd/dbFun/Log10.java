package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Log10 extends BasicFunction<Log10> {
    public Log10(Cmd key) {
        super(SqlConst.LOG10, key);
    }
}
