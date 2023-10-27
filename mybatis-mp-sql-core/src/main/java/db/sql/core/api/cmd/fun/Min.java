package db.sql.core.api.cmd.fun;

import db.sql.api.Cmd;
import db.sql.core.api.tookit.SqlConst;

public class Min extends BasicFunction<Min> {
    public Min(Cmd value) {
        super(SqlConst.MIN, value);
    }
}
