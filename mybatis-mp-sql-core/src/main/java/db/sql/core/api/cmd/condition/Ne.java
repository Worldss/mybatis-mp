package db.sql.core.api.cmd.condition;

import db.sql.api.cmd.Cmd;
import db.sql.core.api.cmd.basic.BasicValue;
import db.sql.core.api.tookit.SqlConst;

import java.io.Serializable;

public class Ne extends BasicCondition {

    public Ne(Cmd key, Cmd value) {
        super(SqlConst.NE, key, value);
    }

    public Ne(Cmd key, Serializable value) {
        this(key,new BasicValue(value));
    }
}
