package db.sql.api.impl.cmd.condition;

import db.sql.api.Cmd;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.tookit.SqlConst;

import java.io.Serializable;

public class Lt extends BasicCondition {

    public Lt(Cmd key, Cmd value) {
        super(SqlConst.LT, key, value);
    }

    public Lt(Cmd key, Serializable value) {
        this(key, Methods.convert(value));
    }
}
