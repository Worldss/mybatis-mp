package db.sql.core.api.cmd.basic;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.cmd.fun.Case;
import db.sql.core.api.cmd.fun.If;

import java.io.Serializable;

public interface Condition<Field, Value> extends db.sql.api.cmd.basic.Condition, Cmd {
    String getOperator();

    Field getField();

    Value getValue();

    default If if_(Cmd value, Cmd value2) {
        return new If(this, value, value2);
    }

    default If if_(Cmd value, Serializable value2) {
        return new If(this, value, new BasicValue(value2));
    }

    default If if_(Serializable value, Serializable value2) {
        return new If(this, value, value2);
    }

    default Case caseThen(Serializable value) {
        return this.caseThen(new BasicValue(value));
    }

    default Case caseThen(Cmd value) {
        return new Case().when(this, value);
    }
}
