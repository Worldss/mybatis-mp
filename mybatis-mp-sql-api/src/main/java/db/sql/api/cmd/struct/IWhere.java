package db.sql.api.cmd.struct;

import db.sql.api.Cmd;
import db.sql.api.cmd.executor.method.IWhereMethod;

public interface IWhere<SELF extends IWhere, COLUMN, V, CONDITION_CHAIN extends IConditionChain<CONDITION_CHAIN, COLUMN, V>> extends IWhereMethod<SELF, COLUMN, V, CONDITION_CHAIN>, Cmd {
}
