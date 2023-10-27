package db.sql.core.api.cmd.condition;

import db.sql.api.Cmd;
import db.sql.core.api.tookit.SqlConst;

public class Lt extends BasicCondition {

    public Lt(Cmd key, Cmd value) {
        super(SqlConst.LT, key, value);
    }
}
