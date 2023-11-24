package db.sql.api.cmd.struct;

import db.sql.api.Cmd;

public interface Limit<SELF extends Limit> extends Cmd {
    SELF set(int offset, int limit);
}
