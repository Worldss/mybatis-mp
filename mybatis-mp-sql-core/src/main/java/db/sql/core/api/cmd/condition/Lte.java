package db.sql.core.api.cmd.condition;

import db.sql.api.Cmd;
import db.sql.core.api.tookit.SqlConst;

public class Lte extends BasicCondition {

    public Lte(Cmd key, Cmd value) {
        super(SqlConst.LTE, key, value);
    }
}
