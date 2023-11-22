package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class Count extends BasicFunction<Count> {
    public Count(Cmd key) {
        super(SqlConst.COUNT, key);
    }
}
