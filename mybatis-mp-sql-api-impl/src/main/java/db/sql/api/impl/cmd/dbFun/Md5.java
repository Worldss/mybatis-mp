package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.cmd.basic.BasicValue;
import db.sql.api.impl.tookit.SqlConst;

public class Md5 extends BasicFunction<Md5> {

    public Md5(String str) {
        this(new BasicValue(str));
    }

    public Md5(Cmd key) {
        super(SqlConst.MD5, key);
    }
}
