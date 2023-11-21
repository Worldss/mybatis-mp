package db.sql.core.api.cmd.condition;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.cmd.basic.BasicValue;
import db.sql.core.api.tookit.SqlConst;

import java.io.Serializable;

public class Gte extends BasicCondition {

    public Gte(Cmd key, Cmd value) {
        super(SqlConst.GTE, key, value);
    }

    public Gte(Cmd key, Serializable value) {
        this(key,new BasicValue(value));
    }
}
