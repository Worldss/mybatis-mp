package db.sql.core.api.cmd.condition;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.cmd.basic.BasicValue;
import db.sql.core.api.tookit.SqlConst;

import java.io.Serializable;

public class Lt extends BasicCondition {

    public Lt(Cmd key, Cmd value) {
        super(SqlConst.LT, key, value);
    }

    public Lt(Cmd key, Serializable value) {
        this(key,new BasicValue(value));
    }
}
