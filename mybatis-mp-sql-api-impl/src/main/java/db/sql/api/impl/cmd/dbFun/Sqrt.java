package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Sqrt extends BasicFunction<Sqrt> {
    public Sqrt(Cmd value) {
        super(SqlConst.SQRT, value);
    }
}
