package db.sql.api.impl.cmd.condition;

import db.sql.api.Cmd;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.tookit.SqlConst;

import java.io.Serializable;

public class Gte extends BasicCondition {

    public Gte(Cmd key, Cmd value) {
        super(SqlConst.GTE, key, value);
    }

    public Gte(Cmd key, Serializable value) {
        this(key, Methods.convert(value));
    }
}
