package db.sql.core.api.cmd.condition;

import db.sql.api.Cmd;
import db.sql.core.api.tookit.SqlConst;


public class Gt extends BasicCondition {

    public Gt(Cmd key, Cmd value) {
        super(SqlConst.GT, key, value);
    }
}
