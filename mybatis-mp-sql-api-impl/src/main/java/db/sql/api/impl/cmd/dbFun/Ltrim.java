package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Ltrim extends BasicFunction<Ltrim> {
    public Ltrim(Cmd key) {
        super(SqlConst.LTRIM, key);
    }
}
