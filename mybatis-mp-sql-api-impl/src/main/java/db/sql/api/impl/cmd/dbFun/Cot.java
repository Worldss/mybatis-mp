package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Cot extends BasicFunction<Cot> {
    public Cot(Cmd value) {
        super(SqlConst.COT, value);
    }
}
