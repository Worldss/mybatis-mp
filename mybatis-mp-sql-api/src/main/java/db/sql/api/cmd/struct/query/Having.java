package db.sql.api.cmd.struct.query;

import db.sql.api.cmd.basic.Condition;

public interface Having<SELF extends Having> {

    <T> SELF and(Condition condition);

    <T> SELF or(Condition condition);
}
