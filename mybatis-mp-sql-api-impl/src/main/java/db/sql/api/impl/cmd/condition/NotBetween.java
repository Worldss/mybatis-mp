package db.sql.api.impl.cmd.condition;

import db.sql.api.Cmd;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.tookit.SqlConst;

import java.io.Serializable;

public class NotBetween extends Between {
    public NotBetween(Cmd key, Cmd value1, Cmd value2) {
        super(SqlConst.NOT_BETWEEN, key, value1, value2);
    }

    public NotBetween(Cmd key, Serializable value1, Serializable value2) {
        this(key, Methods.convert(value1), Methods.convert(value2));
    }
}
