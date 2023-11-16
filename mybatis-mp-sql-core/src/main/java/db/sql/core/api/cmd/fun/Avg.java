package db.sql.core.api.cmd.fun;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.tookit.SqlConst;

public class Avg extends BasicFunction<Avg> {
    public Avg(Cmd value) {
        super(SqlConst.AVG, value);
    }
}
