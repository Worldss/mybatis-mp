package db.sql.api.cmd.executor.method;

import db.sql.api.cmd.struct.IConditionChain;

public interface WhereMethod<SELF extends WhereMethod, COLUMN, V, CONDITION_CHAIN extends IConditionChain<CONDITION_CHAIN, COLUMN, V>> extends ConditionMethod<SELF, COLUMN, V, CONDITION_CHAIN> {

}
