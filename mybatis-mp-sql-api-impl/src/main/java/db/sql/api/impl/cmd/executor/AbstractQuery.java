package db.sql.api.impl.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.JoinMode;
import db.sql.api.cmd.basic.Condition;
import db.sql.api.cmd.basic.UnionsCmdLists;
import db.sql.api.cmd.executor.Query;
import db.sql.api.cmd.executor.SubQuery;
import db.sql.api.cmd.struct.Joins;
import db.sql.api.cmd.struct.query.Unions;
import db.sql.api.impl.cmd.CmdFactory;
import db.sql.api.impl.cmd.ConditionFactory;
import db.sql.api.impl.cmd.basic.*;
import db.sql.api.impl.cmd.struct.*;
import db.sql.api.impl.cmd.struct.query.*;
import db.sql.api.impl.tookit.SqlConst;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractQuery<SELF extends AbstractQuery, CMD_FACTORY extends CmdFactory> extends BaseExecutor<SELF, CMD_FACTORY>
        implements Query<SELF,
        Table,
        Dataset,
        TableField,
        DatasetField,
        Cmd,
        Object,
        CMD_FACTORY,
        ConditionChain,
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
        Union,
        Unions<Union>
        >, Cmd {

    protected final ConditionFactory conditionFactory;

    protected final CMD_FACTORY $;

    protected Select select;

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
        this.conditionFactory = new ConditionFactory($) {
            @Override
            protected boolean ignoreEmpty() {
                return true;
            }
        };
    }

    @Override
    public CMD_FACTORY $() {
        return $;
    }

    @Override
    public List<Cmd> cmds() {
        return this.cmds;
    }

    @Override
    void initCmdSorts(Map<Class<? extends Cmd>, Integer> cmdSorts) {
        int i = 0;
        cmdSorts.put(Select.class, ++i);
        cmdSorts.put(FromDataset.class, ++i);
        cmdSorts.put(Joins.class, ++i);
        cmdSorts.put(Where.class, ++i);
        cmdSorts.put(GroupBy.class, ++i);
        cmdSorts.put(Having.class, ++i);
        cmdSorts.put(OrderBy.class, ++i);
        cmdSorts.put(Limit.class, ++i);
        cmdSorts.put(ForUpdate.class, ++i);
        cmdSorts.put(Unions.class, ++i);
        cmdSorts.put(UnionsCmdLists.class, ++i);
    }


    @Override
    public Select $select() {
        if (select == null) {
            select = new Select();
            this.append(select);
        }
        return select;
    }

    /**
     * select 子查询
     *
     * @param subQuery
     * @param column
     * @param <T>
     * @return
     */
    public <T> SELF select(db.sql.api.cmd.executor.SubQuery subQuery, Getter<T> column, Function<SubQueryTableField, Cmd> f) {
        return this.select(subQuery, column, 1, f);
    }

    /**
     * select 子查询
     *
     * @param subQuery
     * @param column
     * @param <T>
     * @return
     */
    public <T> SELF select(SubQuery subQuery, Getter<T> column, int storey, Function<SubQueryTableField, Cmd> f) {
        this.select(f.apply(new SubQueryTableField(subQuery, (TableField) subQuery.$(column, storey))));
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
        JoinDataset join = new JoinDataset(mode, mainTable, secondTable, (joinDataset -> {
            return new OnDataset(this.conditionFactory, joinDataset);
        }));
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

    @Override
    public Having $having() {
        if (having == null) {
            having = new Having(this.$);
            this.append(having);
        }
        return having;
    }

    @Override
    public <T> SELF havingAnd(Getter<T> getter, Function<TableField, Condition> f) {
        return this.havingAnd(f.apply($.field(getter)));
    }

    @Override
    public <T> SELF havingOr(Getter<T> getter, Function<TableField, Condition> f) {
        return this.havingOr(f.apply($.field(getter)));
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

    public Unions $unions() {
        if (this.unions == null) {
            this.unions = new Unions();
            this.cmds.add(unions);
        }
        return this.unions;
    }

    @Override
    public SELF union(Query unionQuery) {
        $unions().add(new Union(unionQuery));
        return (SELF) this;
    }

    @Override
    public SELF unionAll(Query unionQuery) {
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

