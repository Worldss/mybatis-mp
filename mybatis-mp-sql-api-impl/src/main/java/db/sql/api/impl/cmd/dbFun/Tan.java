package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Tan extends BasicFunction<Tan> {
    public Tan(Cmd value) {
        super(SqlConst.TAN, value);
    }
}
