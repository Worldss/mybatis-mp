package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Ceil extends BasicFunction<Ceil> {
    public Ceil(Cmd key) {
        super(SqlConst.CEIL, key);
    }
}

