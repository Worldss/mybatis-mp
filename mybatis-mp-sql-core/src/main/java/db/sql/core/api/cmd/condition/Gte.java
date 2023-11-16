package db.sql.core.api.cmd.condition;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.tookit.SqlConst;

public class Gte extends BasicCondition {

    public Gte(Cmd key, Cmd value) {
        super(SqlConst.GTE, key, value);
    }
}
