package db.sql.api.cmd.executor;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.CmdFactory;
import db.sql.api.cmd.JoinMode;
import db.sql.api.cmd.basic.Condition;
import db.sql.api.cmd.executor.method.*;
import db.sql.api.cmd.struct.*;
import db.sql.api.cmd.struct.query.*;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Query<SELF extends Query,
        TABLE extends DATASET,
        DATASET extends Cmd,
        TABLE_FIELD extends DATASET_FILED,
        DATASET_FILED extends Cmd,
        SUB_QUERY_TABLE_FILED extends Cmd,
        COLUMN extends Cmd,
        V,

        CMD_FACTORY extends CmdFactory<TABLE, DATASET, TABLE_FIELD, DATASET_FILED>,
        CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>,
        SELECT extends Select<SELECT>,
        FROM extends From<DATASET>,
        JOIN extends Join<JOIN, DATASET, ON>,
        ON extends On<ON, DATASET, COLUMN, V, JOIN, CONDITION_CHAIN>,
        JOINS extends Joins<JOIN>,
        WHERE extends Where<WHERE, COLUMN, V, CONDITION_CHAIN>,
        GROUPBY extends GroupBy<GROUPBY, COLUMN>,
        HAVING extends Having<HAVING>,
        ORDERBY extends OrderBy<ORDERBY>,
        LIMIT extends Limit<LIMIT>,
        FORUPDATE extends ForUpdate<FORUPDATE>,
        UNION extends Union,
        UNIONS extends Unions<UNION>
        >
        extends SelectMethod<SELF, TABLE_FIELD, SUB_QUERY_TABLE_FILED>,
        FromMethod<SELF, DATASET>,
        JoinMethod<SELF, DATASET, ON>,
        WhereMethod<SELF, COLUMN, V, CONDITION_CHAIN>,
        GroupByMethod<SELF, TABLE_FIELD, SUB_QUERY_TABLE_FILED, COLUMN>,
        HavingMethod<SELF, TABLE_FIELD, SUB_QUERY_TABLE_FILED, HAVING>,
        OrderByMethod<SELF, TABLE_FIELD, SUB_QUERY_TABLE_FILED, COLUMN>,
        LimitMethod<SELF>,
        ForUpdateMethod<SELF>,
        UnionMethod<SELF>,
        Executor<SELF, TABLE, DATASET, TABLE_FIELD, DATASET_FILED> {

    CMD_FACTORY $();

    SELECT $select();

    FROM $from(DATASET... tables);

    JOIN $join(JoinMode mode, DATASET mainTable, DATASET secondTable);

    WHERE $where();

    GROUPBY $groupBy();

    HAVING $having();

    ORDERBY $orderBy();

    LIMIT $limit();

    FORUPDATE $forUpdate();

    @Override
    default SELF select(Cmd column) {
        $select().select(column);
        return (SELF) this;
    }

    @Override
    default SELF select(Class entity, int storey) {
        return this.select($().allField($(entity, storey)));
    }


    @Override
    default SELF selectDistinct() {
        $select().distinct();
        return (SELF) this;
    }

    @Override
    default <T> SELF select(Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f) {
        TABLE_FIELD field = this.$().field(column, storey);
        if (f != null) {
            return this.select(f.apply(field));
        } else {
            return this.select(field);
        }
    }

    @Override
    default SELF from(DATASET... tables) {
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
    default SELF orderBy(Cmd column, boolean asc) {
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
