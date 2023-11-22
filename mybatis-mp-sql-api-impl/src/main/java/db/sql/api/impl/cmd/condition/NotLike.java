package db.sql.api.impl.cmd.condition;

import db.sql.api.Cmd;
import db.sql.api.cmd.LikeMode;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.tookit.SqlConst;

public class NotLike extends Like {

    public NotLike(Cmd key, Cmd value) {
        this(key, value, LikeMode.DEFAULT);
    }

    public NotLike(Cmd key, String value) {
        this(key, Methods.convert(value), LikeMode.DEFAULT);
    }

    public NotLike(Cmd key, Cmd value, LikeMode mode) {
        super(SqlConst.NOT_LIKE, key, value, mode);
    }

    public NotLike(Cmd key, String value, LikeMode mode) {
        super(SqlConst.NOT_LIKE, key, Methods.convert(value), mode);
    }
}
