package db.sql.core.api.cmd.fun;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.tookit.SqlConst;

public class Sum extends BasicFunction<Sum> {

    public Sum(Cmd value) {
        super(SqlConst.SUM, value);
    }
}
