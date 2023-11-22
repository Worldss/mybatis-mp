package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Exp extends BasicFunction<Exp> {
    public Exp(Cmd key) {
        super(SqlConst.EXP, key);
    }
}
