package db.sql.api.impl.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.JoinMode;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.basic.UnionsCmdLists;
import db.sql.api.cmd.executor.IQuery;
import db.sql.api.cmd.executor.ISubQuery;
import db.sql.api.cmd.struct.Joins;
import db.sql.api.cmd.struct.query.Unions;
import db.sql.api.cmd.struct.query.Withs;
import db.sql.api.impl.cmd.CmdFactory;
import db.sql.api.impl.cmd.ConditionFactory;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.cmd.basic.DatasetField;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.impl.cmd.struct.*;
import db.sql.api.impl.cmd.struct.query.*;
import db.sql.api.impl.tookit.SqlConst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractQuery<SELF extends AbstractQuery, CMD_FACTORY extends CmdFactory> extends BaseExecutor<SELF, CMD_FACTORY>
        implements IQuery<SELF,
        Table,
        Dataset,
        TableField,
        DatasetField,
        Cmd,
        Object,
        CMD_FACTORY,
        ConditionChain,
        With,
        Select,
        FromDataset,
        JoinDataset,
        OnDataset,
        Joins<JoinDataset>,
        Where,
        GroupBy,
        Having,
        OrderBy,
        Limit,
        ForUpdate,
        Union
        >, Cmd {

    protected final ConditionFactory conditionFactory;

    protected final CMD_FACTORY $;

    protected Select select;

    protected Withs withs;

    protected FromDataset from;

    protected Where where;

    protected Joins joins;

    protected GroupBy groupBy;

    protected Having having;

    protected OrderBy orderBy;

    protected Limit limit;

    protected ForUpdate forUpdate;

    protected Unions unions;

    public AbstractQuery(CMD_FACTORY $) {
        this.$ = $;
        this.conditionFactory = new ConditionFactory($);
    }

    public AbstractQuery(Where where) {
        this.$ = (CMD_FACTORY) where.getConditionFactory().getCmdFactory();
        this.conditionFactory = where.getConditionFactory();
        this.where = where;
        this.append(where);
    }

    @Override
    public CMD_FACTORY $() {
        return $;
    }

    @Override
    void initCmdSorts(Map<Class<? extends Cmd>, Integer> cmdSorts) {
        int i = 0;
        cmdSorts.put(Withs.class, i += 10);
        cmdSorts.put(Select.class, i += 10);
        cmdSorts.put(FromDataset.class, i += 10);
        cmdSorts.put(Joins.class, i += 10);
        cmdSorts.put(Where.class, i += 10);
        cmdSorts.put(GroupBy.class, i += 10);
        cmdSorts.put(Having.class, i += 10);
        cmdSorts.put(OrderBy.class, i += 10);
        cmdSorts.put(Limit.class, i += 10);
        cmdSorts.put(ForUpdate.class, i += 10);
        cmdSorts.put(Unions.class, i += 10);
        cmdSorts.put(UnionsCmdLists.class, i += 10);
    }

    @Override
    public With $with(ISubQuery subQuery) {
        if (Objects.isNull(this.withs)) {
            this.withs = new Withs();
            this.append(this.withs);
        }
        With with = new With(subQuery);
        this.withs.add(with);
        return with;
    }

    @Override
    public Select $select() {
        if (select == null) {
            select = new Select();
            this.append(select);
        }
        return select;
    }

    @Override
    public SELF select(List<Cmd> columns) {
        this.$select().select(columns);
        return (SELF) this;
    }

    @Override
    public <T> SELF select(int storey, Getter<T>... columns) {
        List<Cmd> list = new ArrayList<>(columns.length);
        for (Getter<T> column : columns) {
            list.add($().field(column, storey));
        }
        return this.select(list);
    }


    /**
     * select 子查询 列
     *
     * @param subQuery
     * @param column
     * @param <T>
     * @return
     */
    @Override
    public <T> SELF select(ISubQuery subQuery, Getter<T> column, Function<DatasetField, Cmd> f) {
        return this.select(subQuery, $.columnName(column), f);
    }

    @Override
    public SELF select(ISubQuery subQuery, String columnName, Function<DatasetField, Cmd> f) {
        DatasetField datasetField = $((Dataset) subQuery, columnName);
        if (Objects.nonNull(f)) {
            this.select(f.apply(datasetField));
        } else {
            this.select(datasetField);
        }
        return (SELF) this;
    }

    @Override
    public FromDataset $from(Dataset... tables) {
        if (this.from == null) {
            from = new FromDataset();
            this.append(from);
        }
        this.from.append(tables);
        return from;
    }

    @Override
    public SELF from(Class entity, int storey, Consumer<Dataset> consumer) {
        this.fromEntityIntercept(entity, storey);
        Table table = $(entity, storey);
        this.from(table);
        return (SELF) this;
    }

    @Override
    public JoinDataset $join(JoinMode mode, Dataset mainTable, Dataset secondTable) {
        JoinDataset join = new JoinDataset(mode, mainTable, secondTable, (joinDataset -> new OnDataset(this.conditionFactory, joinDataset)));
        if (Objects.isNull(joins)) {
            joins = new Joins();
            this.append(joins);
        }
        joins.add(join);
        return join;
    }

    @Override
    public SELF join(JoinMode mode, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<OnDataset> consumer) {
        consumer = this.joinEntityIntercept(mainTable, mainTableStorey, secondTable, secondTableStorey, consumer);
        return this.join(mode, $(mainTable, mainTableStorey), $(secondTable, secondTableStorey), consumer);
    }

    @Override
    public SELF join(JoinMode mode, Class mainTable, int mainTableStorey, Dataset secondTable, Consumer<OnDataset> consumer) {
        return this.join(mode, $(mainTable, mainTableStorey), secondTable, consumer);
    }

    @Override
    public Where $where() {
        if (where == null) {
            where = new Where(this.conditionFactory);
            this.append(where);
        }
        return where;
    }

    @Override
    public SELF join(JoinMode mode, Dataset mainTable, Dataset secondTable, Consumer<OnDataset> consumer) {
        JoinDataset join = $join(mode, mainTable, secondTable);
        if (consumer != null) {
            consumer.accept(join.getOn());
        }
        return (SELF) this;
    }

    @Override
    public GroupBy $groupBy() {
        if (groupBy == null) {
            groupBy = new GroupBy();
            this.append(groupBy);
        }
        return groupBy;
    }

    @Override
    public <T> SELF groupBy(Getter<T> column, int storey, Function<TableField, Cmd> f) {
        TableField tableField = $.field(column, storey);
        if (f != null) {
            return this.groupBy(f.apply(tableField));
        }
        return this.groupBy(tableField);
    }

    /**
     * groupBy 子查询 列
     *
     * @param subQuery
     * @param column
     * @param f
     * @param <T>
     * @return
     */
    @Override
    public <T> SELF groupBy(ISubQuery subQuery, Getter<T> column, Function<DatasetField, Cmd> f) {
        return this.groupBy(subQuery, $.columnName(column), f);
    }

    /**
     * groupBy 子查询 列
     *
     * @param subQuery
     * @param columnName
     * @param f
     * @return
     */
    @Override
    public SELF groupBy(ISubQuery subQuery, String columnName, Function<DatasetField, Cmd> f) {
        DatasetField datasetField = $((Dataset) subQuery, columnName);
        if (Objects.nonNull(f)) {
            this.groupBy(f.apply(datasetField));
        } else {
            this.groupBy(datasetField);
        }
        return (SELF) this;
    }

    @Override
    public Having $having() {
        if (having == null) {
            having = new Having(this.$);
            this.append(having);
        }
        return having;
    }

    @Override
    public <T> SELF havingAnd(Getter<T> column, int storey, Function<TableField, ICondition> f) {
        return this.havingAnd(f.apply($(column, storey)));
    }

    @Override
    public <T> SELF havingOr(Getter<T> column, int storey, Function<TableField, ICondition> f) {
        return this.havingOr(f.apply($(column, storey)));
    }

    @Override
    public <T> SELF havingAnd(ISubQuery subQuery, Getter<T> column, Function<DatasetField, ICondition> f) {
        return this.havingAnd(subQuery, $.columnName(column), f);
    }

    @Override
    public <T> SELF havingOr(ISubQuery subQuery, Getter<T> column, Function<DatasetField, ICondition> f) {
        return this.havingOr(subQuery, $.columnName(column), f);
    }

    @Override
    public SELF havingAnd(ISubQuery subQuery, String columnName, Function<DatasetField, ICondition> f) {
        DatasetField datasetField = $((Dataset) subQuery, columnName);
        return this.havingAnd(f.apply(datasetField));
    }

    @Override
    public SELF havingOr(ISubQuery subQuery, String columnName, Function<DatasetField, ICondition> f) {
        DatasetField datasetField = $((Dataset) subQuery, columnName);
        return this.havingOr(f.apply(datasetField));
    }

    @Override
    public OrderBy $orderBy() {
        if (orderBy == null) {
            orderBy = new OrderBy();
            this.append(orderBy);
        }
        return orderBy;
    }


    @Override
    public ForUpdate $forUpdate() {
        if (forUpdate == null) {
            forUpdate = new ForUpdate();
            this.append(forUpdate);
        }
        return forUpdate;
    }

    @Override
    public Limit $limit() {
        if (this.limit == null) {
            this.limit = new Limit(0, 0);
            this.append(this.limit);
        }
        return this.limit;
    }

    @Override
    public <T> SELF orderBy(Getter<T> column, int storey, boolean asc, Function<TableField, Cmd> f) {
        TableField tableField = $.field(column, storey);
        if (f != null) {
            return this.orderBy(f.apply(tableField), asc);
        }
        return this.orderBy(tableField, asc);
    }

    /**
     * orderBy 子查询 列
     *
     * @param subQuery
     * @param column
     * @param asc
     * @param f
     * @param <T>
     * @return
     */
    @Override
    public <T> SELF orderBy(ISubQuery subQuery, Getter<T> column, boolean asc, Function<DatasetField, Cmd> f) {
        return this.orderBy(subQuery, $.columnName(column), asc, f);
    }

    @Override
    public SELF orderBy(ISubQuery subQuery, String columnName, boolean asc, Function<DatasetField, Cmd> f) {
        DatasetField datasetField = $((Dataset) subQuery, columnName);
        if (Objects.nonNull(f)) {
            this.orderBy(f.apply(datasetField), asc);
        } else {
            this.orderBy(datasetField, asc);
        }
        return (SELF) this;
    }

    public Unions $unions() {
        if (this.unions == null) {
            this.unions = new Unions();
            this.cmds.add(unions);
        }
        return this.unions;
    }

    @Override
    public SELF union(IQuery unionQuery) {
        $unions().add(new Union(unionQuery));
        return (SELF) this;
    }

    @Override
    public SELF unionAll(IQuery unionQuery) {
        $unions().add(new Union(SqlConst.UNION_ALL, unionQuery));
        return (SELF) this;
    }

    @Override
    public Select getSelect() {
        return this.select;
    }

    @Override
    public FromDataset getFrom() {
        return this.from;
    }

    @Override
    public Joins getJoins() {
        return this.joins;
    }

    @Override
    public Where getWhere() {
        return this.where;
    }

    @Override
    public GroupBy getGroupBy() {
        return this.groupBy;
    }

    @Override
    public OrderBy getOrderBy() {
        return this.orderBy;
    }

    @Override
    public Limit getLimit() {
        return this.limit;
    }

    @Override
    public Unions getUnions() {
        return this.unions;
    }

    @Override
    public ForUpdate getForUpdate() {
        return forUpdate;
    }
}

