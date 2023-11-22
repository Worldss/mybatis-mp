package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Reverse extends BasicFunction<Reverse> {
    public Reverse(Cmd value) {
        super(SqlConst.REVERSE, value);
    }
}
