package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Date extends BasicFunction<Date> {
    public Date(Cmd key) {
        super(SqlConst.DATE, key);
    }
}