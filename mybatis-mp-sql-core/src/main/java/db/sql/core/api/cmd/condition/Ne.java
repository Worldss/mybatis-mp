package db.sql.core.api.cmd.condition;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.tookit.SqlConst;

public class Ne extends BasicCondition {

    public Ne(Cmd key, Cmd value) {
        super(SqlConst.NE, key, value);
    }
}
