package db.sql.api.executor;

import db.sql.api.*;

public interface Update<SELF extends Update, TABLE, COLUMN, V, CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>, UPDATE_TABLE extends UpdateTable<TABLE>, JOIN extends Join<JOIN, TABLE, ON>, ON extends On<ON, TABLE, COLUMN, V, JOIN, CONDITION_CHAIN>, WHERE extends Where<WHERE, COLUMN, V, CONDITION_CHAIN>>
        extends UpdateMethod<SELF, TABLE, COLUMN, V>, JoinMethod<SELF, TABLE, ON>, WhereMethod<SELF, COLUMN, V, CONDITION_CHAIN> {

    UPDATE_TABLE $update(TABLE... tables);

    JOIN $join(JoinMode mode, TABLE mainTable, TABLE secondTable);

    WHERE $where();

    @Override
    default SELF update(TABLE... tables) {
        $update(tables);
        return (SELF) this;
    }

    @Override
    default CONDITION_CHAIN conditionChain() {
        return $where().conditionChain();
    }
}
