package db.sql.api.cmd.struct.query;

import db.sql.api.Cmd;
import db.sql.api.cmd.basic.Condition;

public interface Having<SELF extends Having> extends Cmd {

    <T> SELF and(Condition condition);

    <T> SELF or(Condition condition);
}
