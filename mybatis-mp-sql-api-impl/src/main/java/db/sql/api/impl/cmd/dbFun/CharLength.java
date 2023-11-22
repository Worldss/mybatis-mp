package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.tookit.SqlConst;

public class CharLength extends BasicFunction<CharLength> {
    public CharLength(Cmd key) {
        super(SqlConst.CHAR_LENGTH, key);
    }
}
