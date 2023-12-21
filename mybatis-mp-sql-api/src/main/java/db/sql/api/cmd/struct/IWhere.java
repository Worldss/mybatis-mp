package db.sql.api.cmd.struct;

import db.sql.api.Cmd;
import db.sql.api.cmd.executor.method.IWhereMethod;
import db.sql.api.cmd.struct.conditionChain.IConditionChain;

public interface IWhere<SELF extends IWhere, TABLE_FIELD, COLUMN, V, CONDITION_CHAIN extends IConditionChain<CONDITION_CHAIN, TABLE_FIELD, COLUMN, V>> extends IWhereMethod<SELF, TABLE_FIELD, COLUMN, V, CONDITION_CHAIN>, Cmd {
}
