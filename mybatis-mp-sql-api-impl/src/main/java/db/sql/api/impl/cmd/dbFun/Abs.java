package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Abs extends BasicFunction<Abs> {
    public Abs(Cmd value) {
        super(SqlConst.ABS, value);
    }
}
