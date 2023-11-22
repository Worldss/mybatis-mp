package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Length extends BasicFunction<Length> {
    public Length(Cmd value) {
        super(SqlConst.LENGTH, value);
    }
}
