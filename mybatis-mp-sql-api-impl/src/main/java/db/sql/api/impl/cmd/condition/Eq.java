package db.sql.api.impl.cmd.condition;


import db.sql.api.Cmd;
import db.sql.api.impl.cmd.basic.BasicValue;
import db.sql.api.impl.tookit.SqlConst;

import java.io.Serializable;

public class Eq extends BasicCondition {

    public Eq(Cmd key, Cmd value) {
        super(SqlConst.EQ, key, value);
    }

    public Eq(Cmd key, Serializable value) {
        this(key,new BasicValue(value));
    }
}
