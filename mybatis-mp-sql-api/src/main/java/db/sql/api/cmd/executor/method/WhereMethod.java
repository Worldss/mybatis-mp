package db.sql.api.cmd.executor.method;

import db.sql.api.cmd.struct.ConditionChain;

public interface WhereMethod<SELF extends WhereMethod, COLUMN, V, CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>> extends ConditionMethod<SELF, COLUMN, V, CONDITION_CHAIN> {

}
