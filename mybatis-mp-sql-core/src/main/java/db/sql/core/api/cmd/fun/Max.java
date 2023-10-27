package db.sql.core.api.cmd.fun;

import db.sql.api.Cmd;
import db.sql.core.api.tookit.SqlConst;

public class Max extends BasicFunction<Max> {
    public Max(Cmd value) {
        super(SqlConst.MAX, value);
    }
}
