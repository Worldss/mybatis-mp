package db.sql.core.api.cmd.condition;

import db.sql.api.cmd.Cmd;
import db.sql.api.cmd.LikeMode;
import db.sql.core.api.tookit.SqlConst;

public class NotLike extends Like {

    public NotLike(Cmd key, Cmd value) {
        this(key, value, LikeMode.DEFAULT);
    }

    public NotLike(Cmd key, Cmd value, LikeMode mode) {
        super(SqlConst.NOT_LIKE, key, value, mode);
    }
}
