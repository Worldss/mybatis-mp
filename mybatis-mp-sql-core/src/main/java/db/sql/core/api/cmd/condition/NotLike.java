package db.sql.core.api.cmd.condition;

import db.sql.api.cmd.Cmd;
import db.sql.api.cmd.LikeMode;
import db.sql.core.api.cmd.basic.BasicValue;
import db.sql.core.api.tookit.SqlConst;

public class NotLike extends Like {

    public NotLike(Cmd key, Cmd value) {
        this(key, value, LikeMode.DEFAULT);
    }

    public NotLike(Cmd key, String value) {
        this(key, new BasicValue(value), LikeMode.DEFAULT);
    }

    public NotLike(Cmd key, Cmd value, LikeMode mode) {
        super(SqlConst.NOT_LIKE, key, value, mode);
    }

    public NotLike(Cmd key, String value, LikeMode mode) {
        super(SqlConst.NOT_LIKE, key, new BasicValue(value), mode);
    }
}
