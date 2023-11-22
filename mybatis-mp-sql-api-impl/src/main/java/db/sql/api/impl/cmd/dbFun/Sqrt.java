package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Sqrt extends BasicFunction<Sqrt> {
    public Sqrt(Cmd key) {
        super(SqlConst.SQRT, key);
    }
}
