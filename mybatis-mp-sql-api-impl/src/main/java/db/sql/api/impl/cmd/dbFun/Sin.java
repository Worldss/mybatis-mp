package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Sin extends BasicFunction<Sin> {
    public Sin(Cmd value) {
        super(SqlConst.SIN, value);
    }
}
