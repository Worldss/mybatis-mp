package db.sql.api.impl.cmd.condition;

import db.sql.api.Cmd;
import db.sql.api.impl.cmd.basic.BasicValue;
import db.sql.api.impl.tookit.SqlConst;

import java.io.Serializable;


public class Gt extends BasicCondition {

    public Gt(Cmd key, Cmd value) {
        super(SqlConst.GT, key, value);
    }

    public Gt(Cmd key, Serializable value) {
        this(key,new BasicValue(value));
    }
}
