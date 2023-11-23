package db.sql.api.cmd.executor;


import db.sql.api.cmd.JoinMode;
import db.sql.api.cmd.basic.Condition;
import db.sql.api.cmd.executor.method.*;
import db.sql.api.cmd.struct.*;
import db.sql.api.cmd.struct.query.*;

import java.util.function.Consumer;

public interface Query<SELF extends Query, TABLE, TABLE_FIELD, COLUMN, V,
        CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>,
        SELECT extends Select<SELECT, COLUMN>, FROM extends From<TABLE>,
        JOIN extends Join<JOIN, TABLE, ON>,
        ON extends On<ON, TABLE, COLUMN, V, JOIN, CONDITION_CHAIN>,
        JOINS extends Joins<JOIN>,
        WHERE extends Where<WHERE, COLUMN, V, CONDITION_CHAIN>,
        GROUPBY extends GroupBy<GROUPBY, COLUMN>,
        HAVING extends Having<HAVING>,
        ORDERBY extends OrderBy<ORDERBY, COLUMN>,
        LIMIT extends Limit<LIMIT>,
        FORUPDATE extends ForUpdate<FORUPDATE>,
        UNION extends Union,
        UNIONS extends Unions<UNION>
        >
        extends SelectMethod<SELF, TABLE_FIELD, COLUMN>,
        FromMethod<SELF, TABLE>, JoinMethod<SELF, TABLE, ON>,
        WhereMethod<SELF, COLUMN, V, CONDITION_CHAIN>,
        GroupByMethod<SELF, TABLE_FIELD, COLUMN>,
        HavingMethod<SELF, TABLE_FIELD, COLUMN, V, CONDITION_CHAIN, HAVING>,
        OrderByMethod<SELF, TABLE_FIELD, COLUMN>,
        LimitMethod<SELF>,
        ForUpdateMethod<SELF>,
        UnionMethod<SELF>,
        Executor<SELF> {

    SELECT $select();

    FROM $from(TABLE... tables);

    JOIN $join(JoinMode mode, TABLE mainTable, TABLE secondTable);

    WHERE $where();

    GROUPBY $groupBy();

    HAVING $having();

    ORDERBY $orderBy();

    LIMIT $limit();

    FORUPDATE $forUpdate();

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
    default SELF havingAnd(Condition condition) {
        $having().and(condition);
        return (SELF) this;
    }

    @Override
    default SELF havingOr(Condition condition) {
        $having().or(condition);
        return (SELF) this;
    }

    @Override
    default SELF orderBy(COLUMN column, boolean asc) {
        $orderBy().orderBy(column, asc);
        return (SELF) this;
    }

    @Override
    default SELF limit(int limit) {
        return this.limit(0, limit);
    }

    @Override
    default SELF limit(int offset, int limit) {
        $limit().set(offset, limit);
        return (SELF) this;
    }

    @Override
    default SELF forUpdate() {
        $forUpdate();
        return (SELF) this;
    }

    SELECT getSelect();

    WHERE getWhere();

    FROM getFrom();

    JOINS getJoins();

    GROUPBY getGroupBy();

    ORDERBY getOrderBy();

    LIMIT getLimit();

    FORUPDATE getForUpdate();

    UNIONS getUnions();

    @Override
    default CONDITION_CHAIN conditionChain() {
        return $where().conditionChain();
    }
}
