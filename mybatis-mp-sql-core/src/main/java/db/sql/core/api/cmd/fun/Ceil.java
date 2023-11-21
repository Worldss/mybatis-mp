package db.sql.core.api.cmd.fun;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.tookit.SqlConst;

public class Ceil extends BasicFunction<Ceil> {
    public Ceil(Cmd value) {
        super(SqlConst.CEIL, value);
    }
}

