package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Cos extends BasicFunction<Cos> {
    public Cos(Cmd key) {
        super(SqlConst.COS, key);
    }
}
