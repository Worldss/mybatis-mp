package db.sql.core.api.cmd.fun;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.tookit.SqlConst;

public class Sign extends BasicFunction<Sign> {
    public Sign(Cmd value) {
        super(SqlConst.SIGN, value);
    }
}
