package db.sql.api.impl.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.ColumnField;
import db.sql.api.cmd.GetterColumnField;
import db.sql.api.cmd.IColumnField;
import db.sql.api.cmd.JoinMode;
import db.sql.api.cmd.basic.IColumn;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.basic.IOrderByDirection;
import db.sql.api.cmd.basic.UnionsCmdLists;
import db.sql.api.cmd.executor.IQuery;
import db.sql.api.cmd.executor.ISubQuery;
import db.sql.api.cmd.struct.Joins;
import db.sql.api.cmd.struct.query.Unions;
import db.sql.api.cmd.struct.query.Withs;
import db.sql.api.impl.cmd.CmdFactory;
import db.sql.api.impl.cmd.ConditionFactory;
import db.sql.api.impl.cmd.basic.*;
import db.sql.api.impl.cmd.struct.*;
import db.sql.api.impl.cmd.struct.query.*;
import db.sql.api.impl.tookit.SqlConst;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractQuery<SELF extends AbstractQuery,
        CMD_FACTORY extends CmdFactory>

        extends BaseExecutor<SELF, CMD_FACTORY>
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

    /**
     * select 列
     *
     * @param column 列
     * @param storey 列存储层级
     * @param <T>    列的实体类
     * @return 自己
     */
    @Override
    public <T> SELF select(Getter<T> column, int storey) {
        return this.select($(column, storey));
    }


    /**
     * select 子查询 列
     *
     * @param column 列
     * @param storey 列存储层级
     * @param <T>    列的实体类
     * @param f      转换函数
     * @return 自己
     */
    @Override
    public <T> SELF selectWithFun(Getter<T> column, int storey, Function<TableField, Cmd> f) {
        return this.select(f.apply($(column, storey)));
    }


    @Override
    public <T> SELF selectWithFun(Function<TableField[], Cmd> f, int storey, Getter<T>... columns) {
        return this.select(f.apply($.fields(storey, columns)));
    }

    @Override
    public SELF selectWithFun(Function<TableField[], Cmd> f, GetterColumnField... getterColumnFields) {
        return this.select(f.apply($.fields(getterColumnFields)));
    }

    @Override
    public <T> SELF select(int storey, Getter<T>... columns) {
        return this.select($.fields(storey, columns));
    }

    @Override
    public SELF select(String columnName) {
        return this.select($.column(columnName));
    }

    @Override
    public SELF selectWithFun(String columnName, Function<IColumn, Cmd> f) {
        return this.select(f.apply($.column(columnName)));
    }

    /**
     * select 子查询 列
     *
     * @param subQuery 子查询
     * @param column   列
     * @param <T>      列的实体类
     * @return
     */
    @Override
    public <T> SELF select(ISubQuery subQuery, Getter<T> column) {
        return this.select(subQuery, $.columnName(column));
    }

    /**
     * select 子查询 列
     *
     * @param subQuery 子查询
     * @param column   列
     * @param f        转换函数
     * @param <T>      列的实体类
     * @return
     */
    @Override
    public <T> SELF selectWithFun(ISubQuery subQuery, Getter<T> column, Function<DatasetField, Cmd> f) {
        return this.selectWithFun(subQuery, $.columnName(column), f);
    }

    /**
     * select 子查询 列
     *
     * @param subQuery   子查询
     * @param columnName 列
     * @param f          转换函数
     * @return
     */
    @Override
    public SELF selectWithFun(ISubQuery subQuery, String columnName, Function<DatasetField, Cmd> f) {
        return this.select(f.apply($((Dataset) subQuery, columnName)));
    }


    @Override
    public <T> SELF selectWithFun(ISubQuery subQuery, Function<DatasetField[], Cmd> f, Getter<T>... columns) {
        return this.select(this.apply(subQuery, f, columns));
    }

    @Override
    public SELF selectWithFun(ISubQuery subQuery, Function<DatasetField[], Cmd> f, IColumnField... columnFields) {
        return this.select(this.apply(subQuery, f, columnFields));
    }

    @Override
    public SELF select(ISubQuery subQuery, String columnName) {
        return this.select($.field((Dataset) subQuery, columnName));
    }


    @Override
    public <T> SELF select(ISubQuery subQuery, Getter<T> column, Function<DatasetField, Cmd> f) {
        return this.select(f.apply($((Dataset) subQuery, column)));
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
    public <T> SELF and(Getter<T> column, int storey, Function<TableField, ICondition> function) {
        $where().and(column, storey, function);
        return (SELF) this;
    }

    @Override
    public <T> SELF or(Getter<T> column, int storey, Function<TableField, ICondition> function) {
        $where().or(column, storey, function);
        return (SELF) this;
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


    /**
     * groupBy 列
     *
     * @param column 列
     * @param storey 列存储层级
     * @param <T>    列的实体类
     * @return 自己
     */
    @Override
    public <T> SELF groupBy(Getter<T> column, int storey) {
        return this.groupBy($(column, storey));
    }


    /**
     * groupBy 子查询 列
     *
     * @param column 列
     * @param storey 列存储层级
     * @param <T>    列的实体类
     * @param f      转换函数
     * @return 自己
     */
    @Override
    public <T> SELF groupByWithFun(Getter<T> column, int storey, Function<TableField, Cmd> f) {
        return this.groupBy(f.apply($(column, storey)));
    }


    @Override
    public <T> SELF groupByWithFun(Function<TableField[], Cmd> f, int storey, Getter<T>... columns) {
        return this.groupBy(f.apply($.fields(storey, columns)));
    }

    @Override
    public SELF groupByWithFun(Function<TableField[], Cmd> f, GetterColumnField... getterColumnFields) {
        return this.groupBy(f.apply($.fields(getterColumnFields)));
    }

    @Override
    public <T> SELF groupBy(int storey, Getter<T>... columns) {
        return this.groupBy($.fields(storey, columns));
    }

    @Override
    public SELF groupBy(String columnName) {
        return this.groupBy($.column(columnName));
    }

    @Override
    public SELF groupByWithFun(String columnName, Function<IColumn, Cmd> f) {
        return this.groupBy(f.apply($.column(columnName)));
    }

    /**
     * groupBy 子查询 列
     *
     * @param subQuery 子查询
     * @param column   列
     * @param <T>      列的实体类
     * @return
     */
    @Override
    public <T> SELF groupBy(ISubQuery subQuery, Getter<T> column) {
        return this.groupBy(subQuery, $.columnName(column));
    }

    /**
     * groupBy 子查询 列
     *
     * @param subQuery 子查询
     * @param column   列
     * @param f        转换函数
     * @param <T>      列的实体类
     * @return
     */
    @Override
    public <T> SELF groupByWithFun(ISubQuery subQuery, Getter<T> column, Function<DatasetField, Cmd> f) {
        return this.groupByWithFun(subQuery, $.columnName(column), f);
    }

    /**
     * groupBy 子查询 列
     *
     * @param subQuery   子查询
     * @param columnName 列
     * @param f          转换函数
     * @return
     */
    @Override
    public SELF groupByWithFun(ISubQuery subQuery, String columnName, Function<DatasetField, Cmd> f) {
        return this.groupBy(f.apply($((Dataset) subQuery, columnName)));
    }


    @Override
    public <T> SELF groupByWithFun(ISubQuery subQuery, Function<DatasetField[], Cmd> f, Getter<T>... columns) {
        return this.groupBy(this.apply(subQuery, f, columns));
    }

    @Override
    public SELF groupByWithFun(ISubQuery subQuery, Function<DatasetField[], Cmd> f, IColumnField... columnFields) {
        return this.groupBy(this.apply(subQuery, f, columnFields));
    }

    @Override
    public SELF groupBy(ISubQuery subQuery, String columnName) {
        return this.groupBy($.field((Dataset) subQuery, columnName));
    }


    @Override
    public <T> SELF groupBy(ISubQuery subQuery, Getter<T> column, Function<DatasetField, Cmd> f) {
        return this.groupBy(f.apply($((Dataset) subQuery, column)));
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
    public <T> SELF havingAnd(boolean when, Getter<T> column, int storey, Function<TableField, ICondition> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.havingAnd(f.apply($(column, storey)));
    }

    @Override
    public <T> SELF havingOr(boolean when, Getter<T> column, int storey, Function<TableField, ICondition> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.havingOr(f.apply($(column, storey)));
    }

    @Override
    public <T> SELF havingAnd(ISubQuery subQuery, boolean when, Getter<T> column, Function<DatasetField, ICondition> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.havingAnd(subQuery, $.columnName(column), f);
    }

    @Override
    public <T> SELF havingOr(ISubQuery subQuery, boolean when, Getter<T> column, Function<DatasetField, ICondition> f) {
        if (!when) {
            return (SELF) this;
        }
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
    public <T> SELF havingAnd(boolean when, Function<TableField[], ICondition> f, int storey, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.havingAnd(f.apply($.fields(storey, columns)));
    }

    @Override
    public <T> SELF havingOr(boolean when, Function<TableField[], ICondition> f, int storey, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.havingOr(f.apply($.fields(storey, columns)));
    }

    @Override
    public <T> SELF havingAnd(ISubQuery subQuery, boolean when, Function<DatasetField[], ICondition> f, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.havingAnd(this.apply(subQuery, f, columns));
    }

    @Override
    public <T> SELF havingOr(ISubQuery subQuery, boolean when, Function<DatasetField[], ICondition> f, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.havingOr(this.apply(subQuery, f, columns));
    }

    @Override
    public SELF havingAnd(boolean when, Function<TableField[], ICondition> f, GetterColumnField... getterColumnFields) {
        if (!when) {
            return (SELF) this;
        }
        return this.havingAnd(f.apply($.fields(getterColumnFields)));
    }

    @Override
    public SELF havingOr(boolean when, Function<TableField[], ICondition> f, GetterColumnField... getterColumnFields) {
        if (!when) {
            return (SELF) this;
        }
        return this.havingOr(f.apply($.fields(getterColumnFields)));
    }

    private <T, R> R apply(ISubQuery subQuery, Function<DatasetField[], R> f, Getter<T>... columns) {
        CmdFactory $ = (CmdFactory) subQuery.$();
        DatasetField[] datasetFields = new DatasetField[columns.length];
        for (int i = 0; i < columns.length; i++) {
            datasetFields[i] = $((Dataset) subQuery, $.columnName(columns[i]));
        }
        return f.apply(datasetFields);
    }


    private <R> R apply(ISubQuery subQuery, Function<DatasetField[], R> f, IColumnField... columnFields) {
        CmdFactory $ = (CmdFactory) subQuery.$();
        DatasetField[] datasetFields = new DatasetField[columnFields.length];
        for (int i = 0; i < columnFields.length; i++) {
            IColumnField columnField = columnFields[i];
            if (columnField instanceof ColumnField) {
                datasetFields[i] = $((Dataset) subQuery, ((ColumnField) columnField).getColumnName());
            } else if (columnField instanceof GetterColumnField) {
                datasetFields[i] = $((Dataset) subQuery, $.columnName(((GetterColumnField<?>) columnField).getGetter()));
            } else {
                throw new RuntimeException("Not Supported");
            }
        }
        return f.apply(datasetFields);
    }

    @Override
    public SELF havingAnd(ISubQuery subQuery, boolean when, Function<DatasetField[], ICondition> f, IColumnField... columnFields) {
        if (!when) {
            return (SELF) this;
        }
        return this.havingAnd(this.apply(subQuery, f, columnFields));
    }

    @Override
    public SELF havingOr(ISubQuery subQuery, boolean when, Function<DatasetField[], ICondition> f, IColumnField... columnFields) {
        if (!when) {
            return (SELF) this;
        }
        return this.havingOr(this.apply(subQuery, f, columnFields));
    }

    @Override
    public SELF havingAnd(ISubQuery subQuery, boolean when, String columnName, Function<DatasetField, ICondition> f) {
        if (!when) {
            return (SELF) this;
        }
        DatasetField datasetField = $((Dataset) subQuery, columnName);
        return this.havingAnd(f.apply(datasetField));
    }

    @Override
    public SELF havingOr(ISubQuery subQuery, boolean when, String columnName, Function<DatasetField, ICondition> f) {
        if (!when) {
            return (SELF) this;
        }
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
    public IOrderByDirection defaultOrderByDirection() {
        return OrderByDirection.ASC;
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

    /**
     * orderBy 列
     *
     * @param column 列
     * @param storey 列存储层级
     * @param <T>    列的实体类
     * @return 自己
     */
    @Override
    public <T> SELF orderBy(IOrderByDirection orderByDirection, Getter<T> column, int storey) {
        return this.orderBy(orderByDirection, $(column, storey));
    }


    /**
     * orderBy 列
     *
     * @param column 列
     * @param storey 列存储层级
     * @param <T>    列的实体类
     * @param f      转换函数
     * @return 自己
     */
    @Override
    public <T> SELF orderByWithFun(IOrderByDirection orderByDirection, Getter<T> column, int storey, Function<TableField, Cmd> f) {
        return this.orderBy(orderByDirection, f.apply($(column, storey)));
    }


    @Override
    public <T> SELF orderByWithFun(IOrderByDirection orderByDirection, Function<TableField[], Cmd> f, int storey, Getter<T>... columns) {
        return this.orderBy(orderByDirection, f.apply($.fields(storey, columns)));
    }

    @Override
    public SELF orderByWithFun(IOrderByDirection orderByDirection, Function<TableField[], Cmd> f, GetterColumnField... getterColumnFields) {
        return this.orderBy(orderByDirection, f.apply($.fields(getterColumnFields)));
    }

    @Override
    public <T> SELF orderBy(IOrderByDirection orderByDirection, int storey, Getter<T>... columns) {
        return this.orderBy(orderByDirection, $.fields(storey, columns));
    }

    @Override
    public SELF orderBy(IOrderByDirection orderByDirection, String columnName) {
        return this.orderBy(orderByDirection, $.column(columnName));
    }

    @Override
    public SELF orderByWithFun(IOrderByDirection orderByDirection, String columnName, Function<IColumn, Cmd> f) {
        return this.orderBy(orderByDirection, f.apply($.column(columnName)));
    }

    /**
     * orderBy 子查询 列
     *
     * @param subQuery 子查询
     * @param column   列
     * @param <T>      列的实体类
     * @return
     */
    @Override
    public <T> SELF orderBy(ISubQuery subQuery, IOrderByDirection orderByDirection, Getter<T> column) {
        return this.orderBy(subQuery, orderByDirection, $.columnName(column));
    }

    /**
     * orderBy 子查询 列
     *
     * @param subQuery 子查询
     * @param column   列
     * @param f        转换函数
     * @param <T>      列的实体类
     * @return
     */
    @Override
    public <T> SELF orderByWithFun(ISubQuery subQuery, IOrderByDirection orderByDirection, Getter<T> column, Function<DatasetField, Cmd> f) {
        return this.orderByWithFun(subQuery, orderByDirection, $.columnName(column), f);
    }

    /**
     * orderBy 子查询 列
     *
     * @param subQuery   子查询
     * @param columnName 列
     * @param f          转换函数
     * @return
     */
    @Override
    public SELF orderByWithFun(ISubQuery subQuery, IOrderByDirection orderByDirection, String columnName, Function<DatasetField, Cmd> f) {
        return this.orderBy(orderByDirection, f.apply($((Dataset) subQuery, columnName)));
    }


    @Override
    public <T> SELF orderByWithFun(ISubQuery subQuery, IOrderByDirection orderByDirection, Function<DatasetField[], Cmd> f, Getter<T>... columns) {
        return this.orderBy(orderByDirection, this.apply(subQuery, f, columns));
    }

    @Override
    public SELF orderByWithFun(ISubQuery subQuery, IOrderByDirection orderByDirection, Function<DatasetField[], Cmd> f, IColumnField... columnFields) {
        return this.orderBy(orderByDirection, this.apply(subQuery, f, columnFields));
    }

    @Override
    public SELF orderBy(ISubQuery subQuery, IOrderByDirection orderByDirection, String columnName) {
        return this.orderBy(orderByDirection, $.field((Dataset) subQuery, columnName));
    }


    @Override
    public <T> SELF orderBy(ISubQuery subQuery, IOrderByDirection orderByDirection, Getter<T> column, Function<DatasetField, Cmd> f) {
        return this.orderBy(orderByDirection, f.apply($((Dataset) subQuery, column)));
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

