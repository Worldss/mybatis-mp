package db.sql.core.api.cmd.condition;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.cmd.basic.BasicValue;
import db.sql.core.api.tookit.SqlConst;

import java.io.Serializable;

public class Lte extends BasicCondition {

    public Lte(Cmd key, Cmd value) {
        super(SqlConst.LTE, key, value);
    }


    public Lte(Cmd key, Serializable value) {
        this(key, new BasicValue(value));
    }
}
