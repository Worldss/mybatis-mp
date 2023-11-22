package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Asin extends BasicFunction<Asin> {
    public Asin(Cmd key) {
        super(SqlConst.ASIN, key);
    }
}
