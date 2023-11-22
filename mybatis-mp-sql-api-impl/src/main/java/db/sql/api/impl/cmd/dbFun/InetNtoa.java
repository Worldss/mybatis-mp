package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.cmd.basic.BasicValue;
import db.sql.api.impl.tookit.SqlConst;

public class InetNtoa extends BasicFunction<InetNtoa> {
    public InetNtoa(Number ipNumber) {
        this(new BasicValue(ipNumber));
    }
    public InetNtoa(Cmd key) {
        super(SqlConst.INET_NTOA, key);
    }
}
