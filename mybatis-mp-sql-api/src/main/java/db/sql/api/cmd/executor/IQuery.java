package db.sql.api.cmd.executor;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.ICmdFactory;
import db.sql.api.cmd.JoinMode;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.basic.IOrderByDirection;
import db.sql.api.cmd.executor.method.*;
import db.sql.api.cmd.struct.*;
import db.sql.api.cmd.struct.conditionChain.IConditionChain;
import db.sql.api.cmd.struct.query.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IQuery<SELF extends IQuery,
        TABLE extends DATASET,
        DATASET extends Cmd,
        TABLE_FIELD extends DATASET_FILED,
        DATASET_FILED extends Cmd,
        COLUMN extends Cmd,
        V,

        CMD_FACTORY extends ICmdFactory<TABLE, DATASET, TABLE_FIELD, DATASET_FILED>,
        CONDITION_CHAIN extends IConditionChain<CONDITION_CHAIN, TABLE_FIELD, COLUMN, V>,

        WITH extends IWith<WITH>,
        SELECT extends ISelect<SELECT>,
        FROM extends IFrom<DATASET>,
        JOIN extends IJoin<JOIN, DATASET, ON>,
        ON extends IOn<ON, DATASET, TABLE_FIELD, COLUMN, V, JOIN, CONDITION_CHAIN>,
        JOINS extends Joins<JOIN>,
        WHERE extends IWhere<WHERE, TABLE_FIELD, COLUMN, V, CONDITION_CHAIN>,
        GROUPBY extends IGroupBy<GROUPBY, COLUMN>,
        HAVING extends IHaving<HAVING>,
        ORDERBY extends IOrderBy<ORDERBY>,
        LIMIT extends ILimit<LIMIT>,
        FORUPDATE extends IForUpdate<FORUPDATE>,
        IUNION extends IUnion
        >
        extends IWithMethod<SELF>,
        ISelectMethod<SELF, TABLE_FIELD, DATASET_FILED,COLUMN>,
        IFromMethod<SELF, DATASET>,
        IJoinMethod<SELF, DATASET, ON>,
        IWhereMethod<SELF, TABLE_FIELD, COLUMN, V, CONDITION_CHAIN>,
        IGroupByMethod<SELF, TABLE_FIELD, DATASET_FILED, COLUMN>,
        IHavingMethod<SELF, TABLE_FIELD, DATASET_FILED, HAVING>,
        IOrderByMethod<SELF, TABLE_FIELD, DATASET_FILED, COLUMN>,
        ILimitMethod<SELF>,
        IForUpdateMethod<SELF>,
        IUnionMethod<SELF>,
        IExecutor<SELF, TABLE, DATASET, TABLE_FIELD, DATASET_FILED> {

    CMD_FACTORY $();

    WITH $with(ISubQuery subQuery);

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
    default SELF with(ISubQuery subQuery) {
        $with(subQuery);
        return (SELF) this;
    }

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
    default <T> SELF selectIgnore(Getter<T> column, int storey) {
        this.$select().selectIgnore($(column, storey));
        return (SELF) this;
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
    default SELF groupBy(COLUMN... columns) {
        $groupBy().groupBy(columns);
        return (SELF) this;
    }

    @Override
    default SELF groupBy(List<COLUMN> columns) {
        $groupBy().groupBy(columns);
        return (SELF) this;
    }

    @Override
    default SELF having(Consumer<HAVING> consumer) {
        consumer.accept($having());
        return (SELF) this;
    }

    @Override
    default SELF havingAnd(ICondition condition) {
        $having().and(condition);
        return (SELF) this;
    }

    @Override
    default SELF havingOr(ICondition condition) {
        $having().or(condition);
        return (SELF) this;
    }

    @Override
    default SELF orderBy(IOrderByDirection orderByDirection, Cmd column) {
        $orderBy().orderBy(orderByDirection, column);
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

    Unions getUnions();

    @Override
    default CONDITION_CHAIN conditionChain() {
        return $where().conditionChain();
    }
}
