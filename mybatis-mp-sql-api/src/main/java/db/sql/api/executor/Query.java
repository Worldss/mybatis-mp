package db.sql.api.executor;


import db.sql.api.*;

import java.util.function.Consumer;

public interface Query<SELF extends Query, TABLE, TABLE_FIELD, COLUMN, V,
        CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>,
        SELECT extends Select<SELECT, COLUMN>, FROM extends From<TABLE>,
        JOIN extends Join<JOIN, TABLE, ON>,
        ON extends On<ON, TABLE, COLUMN, V, JOIN, CONDITION_CHAIN>,
        WHERE extends Where<WHERE, COLUMN, V, CONDITION_CHAIN>,
        GROUP extends GroupBy<GROUP, COLUMN>,
        HAVING extends Having<HAVING>,
        ORDER extends OrderBy<ORDER, COLUMN>
        >
        extends SelectMethod<SELF, TABLE_FIELD, COLUMN>,
        FromMethod<SELF, TABLE>, JoinMethod<SELF, TABLE, ON>,
        WhereMethod<SELF, COLUMN, V, CONDITION_CHAIN>,
        GroupByMethod<SELF, TABLE_FIELD, COLUMN>,
        HavingMethod<SELF, COLUMN, V, CONDITION_CHAIN, HAVING>,
        OrderByMethod<SELF, TABLE_FIELD, COLUMN> {

    SELECT $select();

    FROM $from(TABLE... tables);

    JOIN $join(JoinMode mode, TABLE mainTable, TABLE secondTable);

    WHERE $where();

    GROUP $groupBy();

    HAVING $having();

    ORDER $orderBy();

    @Override
    default SELF select(COLUMN column) {
        $select().select(column);
        return (SELF) this;
    }

    @Override
    default SELF from(TABLE... tables) {
        $from(tables);
        return (SELF) this;
    }

    @Override
    default SELF groupBy(COLUMN column) {
        $groupBy().groupBy(column);
        return (SELF) this;
    }

    @Override
    default SELF having(Consumer<HAVING> consumer) {
        consumer.accept($having());
        return (SELF) this;
    }

    @Override
    default SELF orderBy(COLUMN column, boolean asc) {
        $orderBy().orderBy(column, asc);
        return (SELF) this;
    }

    @Override
    default CONDITION_CHAIN conditionChain() {
        return $where().conditionChain();
    }
}
