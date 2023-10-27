package db.sql.api;

public interface Where<SELF extends Where, COLUMN, V, CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>> extends WhereMethod<SELF, COLUMN, V, CONDITION_CHAIN> {


}
