package db.sql.api;

public interface WhereMethod<SELF extends WhereMethod, COLUMN, V, CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>> extends ConditionChainMethod<SELF, COLUMN, V, CONDITION_CHAIN> {


}
