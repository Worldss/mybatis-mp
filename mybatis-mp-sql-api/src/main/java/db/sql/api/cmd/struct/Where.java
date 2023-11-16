package db.sql.api.cmd.struct;

import db.sql.api.cmd.executor.method.WhereMethod;

public interface Where<SELF extends Where, COLUMN, V, CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>> extends WhereMethod<SELF, COLUMN, V, CONDITION_CHAIN> {


}
