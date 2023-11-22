package db.sql.api.impl.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.cmd.dbFun.Case;
import db.sql.api.impl.cmd.dbFun.If;

import java.io.Serializable;

public interface Condition<Field, Value> extends db.sql.api.cmd.basic.Condition, Cmd {
    String getOperator();

    Field getField();

    Value getValue();

    default If if_(Cmd value, Cmd value2) {
        return new If(this, value, value2);
    }

    default If if_(Cmd value, Serializable value2) {
        return new If(this, value, Methods.convert(value2));
    }

    default If if_(Serializable value, Serializable value2) {
        return new If(this, value, value2);
    }

    default Case caseThen(Serializable value) {
        return this.caseThen(Methods.convert(value));
    }

    default Case caseThen(Cmd value) {
        return new Case().when(this, value);
    }
}
