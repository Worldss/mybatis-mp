package db.sql.core.api.cmd.condition;


import db.sql.api.Cmd;
import db.sql.core.api.tookit.SqlConst;

public class Eq extends BasicCondition {

    public Eq(Cmd key, Cmd value) {
        super(SqlConst.EQ, key, value);
    }
}
