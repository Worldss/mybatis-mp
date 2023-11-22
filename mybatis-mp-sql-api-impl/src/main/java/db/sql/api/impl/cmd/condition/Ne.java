package db.sql.api.impl.cmd.condition;

import db.sql.api.Cmd;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.tookit.SqlConst;

import java.io.Serializable;

public class Ne extends BasicCondition {

    public Ne(Cmd key, Cmd value) {
        super(SqlConst.NE, key, value);
    }

    public Ne(Cmd key, Serializable value) {
        this(key, Methods.convert(value));
    }
}
