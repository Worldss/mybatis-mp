package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.cmd.basic.BasicValue;
import db.sql.api.impl.tookit.SqlConst;

public class InetAton extends BasicFunction<InetAton> {

    public InetAton(String ip) {
        this(new BasicValue(ip));
    }

    public InetAton(Cmd key) {
        super(SqlConst.INET_ATON, key);
    }
}
