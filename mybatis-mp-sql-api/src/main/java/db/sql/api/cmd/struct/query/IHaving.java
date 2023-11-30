package db.sql.api.cmd.struct.query;

import db.sql.api.Cmd;
import db.sql.api.cmd.basic.ICondition;

public interface IHaving<SELF extends IHaving> extends Cmd {

    <T> SELF and(ICondition condition);

    <T> SELF or(ICondition condition);
}
