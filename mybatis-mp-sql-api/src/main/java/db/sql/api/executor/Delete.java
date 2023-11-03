package db.sql.api.executor;

import db.sql.api.*;

public interface Delete<SELF extends Delete, TABLE, COLUMN, V, CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>, DELETE_TABLE extends DeleteTable<TABLE>, FROM extends From<TABLE>, JOIN extends Join<JOIN, TABLE, ON>, ON extends On<ON, TABLE, COLUMN, V, JOIN, CONDITION_CHAIN>, WHERE extends Where<WHERE, COLUMN, V, CONDITION_CHAIN>>
        extends DeleteMethod<SELF, TABLE>, FromMethod<SELF, TABLE>, JoinMethod<SELF, TABLE, ON>, WhereMethod<SELF, COLUMN, V, CONDITION_CHAIN> {

    @SuppressWarnings("unchecked")
    DELETE_TABLE $delete(TABLE... tables);

    @SuppressWarnings("unchecked")
    FROM $from(TABLE... tables);

    JOIN $join(JoinMode mode, TABLE mainTable, TABLE secondTable);

    WHERE $where();


    @Override
    @SuppressWarnings("unchecked")
    default SELF delete(TABLE... tables) {
        $delete(tables);
        return (SELF) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    default SELF from(TABLE... tables) {
        $from(tables);
        return (SELF) this;
    }

    @Override
    default CONDITION_CHAIN conditionChain() {
        return $where().conditionChain();
    }
}
