package db.sql.api.cmd.struct;


import db.sql.api.Cmd;
import db.sql.api.cmd.executor.method.ConditionMethod;

public interface IOn<SELF extends IOn, TABLE, COLUMN, V, JOIN extends IJoin<JOIN, TABLE, SELF>, CONDITION_CHAIN extends IConditionChain<CONDITION_CHAIN, COLUMN, V>> extends ConditionMethod<SELF, COLUMN, V, CONDITION_CHAIN>, Cmd {
    JOIN getJoin();
}