package db.sql.api;


public interface On<SELF extends On, TABLE, COLUMN, V, JOIN extends Join<JOIN, TABLE, SELF>, CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>> extends ConditionChainMethod<SELF, COLUMN, V, CONDITION_CHAIN> {
    JOIN getJoin();
}
