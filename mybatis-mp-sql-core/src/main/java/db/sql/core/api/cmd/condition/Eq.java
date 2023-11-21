package db.sql.core.api.cmd.condition;


import db.sql.api.cmd.Cmd;
import db.sql.core.api.cmd.basic.BasicValue;
import db.sql.core.api.tookit.SqlConst;

import java.io.Serializable;

public class Eq extends BasicCondition {

    public Eq(Cmd key, Cmd value) {
        super(SqlConst.EQ, key, value);
    }

    public Eq(Cmd key, Serializable value) {
        this(key,new BasicValue(value));
    }
}
