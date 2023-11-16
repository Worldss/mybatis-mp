package db.sql.core.api.cmd.condition;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.tookit.SqlConst;

public class NotBetween extends Between {
    public NotBetween(Cmd key, Cmd value1, Cmd value2) {
        super(SqlConst.NOT_BETWEEN, key, value1, value2);
    }
}
