package db.sql.api.impl.cmd.condition;

import db.sql.api.Cmd;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.tookit.SqlConst;

import java.io.Serializable;

public class Lte extends BasicCondition {

    public Lte(Cmd key, Cmd value) {
        super(SqlConst.LTE, key, value);
    }


    public Lte(Cmd key, Serializable value) {
        this(key, Methods.convert(value));
    }
}
