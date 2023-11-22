package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Avg extends BasicFunction<Avg> {
    public Avg(Cmd value) {
        super(SqlConst.AVG, value);
    }
}
