package db.sql.api.cmd.struct;

import db.sql.api.Cmd;
import db.sql.api.cmd.executor.method.WhereMethod;

public interface IWhere<SELF extends IWhere, COLUMN, V, CONDITION_CHAIN extends IConditionChain<CONDITION_CHAIN, COLUMN, V>> extends WhereMethod<SELF, COLUMN, V, CONDITION_CHAIN>, Cmd {


}
