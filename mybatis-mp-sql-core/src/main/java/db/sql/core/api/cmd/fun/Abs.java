package db.sql.core.api.cmd.fun;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.tookit.SqlConst;

public class Abs extends BasicFunction<Abs> {
    public Abs(Cmd value) {
        super(SqlConst.ABS, value);
    }
}
