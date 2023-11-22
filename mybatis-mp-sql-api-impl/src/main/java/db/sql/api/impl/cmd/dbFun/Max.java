package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Max extends BasicFunction<Max> {
    public Max(Cmd value) {
        super(SqlConst.MAX, value);
    }
}
