package db.sql.core.api.cmd.fun;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.tookit.SqlConst;

public class Sqrt extends BasicFunction<Sqrt> {
    public Sqrt(Cmd value) {
        super(SqlConst.SQRT, value);
    }
}
