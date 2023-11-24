package db.sql.api.cmd.struct;


import db.sql.api.Cmd;
import db.sql.api.cmd.executor.method.ChainMethod;

public interface On<SELF extends On, TABLE, COLUMN, V, JOIN extends Join<JOIN, TABLE, SELF>, CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>> extends ChainMethod<SELF, COLUMN, V, CONDITION_CHAIN>, Cmd {
    JOIN getJoin();
}
