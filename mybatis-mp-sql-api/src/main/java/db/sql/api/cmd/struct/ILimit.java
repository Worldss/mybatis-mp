package db.sql.api.cmd.struct;

import db.sql.api.Cmd;

public interface ILimit<SELF extends ILimit> extends Cmd {
    SELF set(int offset, int limit);
}
