package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Log2 extends BasicFunction<Log2> {
    public Log2(Cmd key) {
        super(SqlConst.LOG2, key);
    }
}
