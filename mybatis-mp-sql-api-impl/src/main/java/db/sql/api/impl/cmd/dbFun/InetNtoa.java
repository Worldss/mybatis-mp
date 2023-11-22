package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.tookit.SqlConst;

public class InetNtoa extends BasicFunction<InetNtoa> {
    public InetNtoa(Number ipNumber) {
        this(Methods.convert(ipNumber));
    }
    public InetNtoa(Cmd key) {
        super(SqlConst.INET_NTOA, key);
    }
}
