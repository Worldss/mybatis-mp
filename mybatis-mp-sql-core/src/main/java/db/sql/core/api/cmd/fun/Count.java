package db.sql.core.api.cmd.fun;

import db.sql.api.Cmd;
import db.sql.core.api.tookit.SqlConst;

public class Count extends BasicFunction<Count> {
    public Count(Cmd value) {
        super(SqlConst.COUNT, value);
    }
}
