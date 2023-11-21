package db.sql.core.api.cmd.condition;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.cmd.basic.BasicValue;
import db.sql.core.api.tookit.SqlConst;

import java.io.Serializable;


public class Gt extends BasicCondition {

    public Gt(Cmd key, Cmd value) {
        super(SqlConst.GT, key, value);
    }

    public Gt(Cmd key, Serializable value) {
        this(key,new BasicValue(value));
    }
}
