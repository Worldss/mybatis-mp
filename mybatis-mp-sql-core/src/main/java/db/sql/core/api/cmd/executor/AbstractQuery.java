package db.sql.core.api.cmd.executor;

import db.sql.api.*;
import db.sql.core.api.cmd.ConditionChain;
import db.sql.core.api.cmd.From;
import db.sql.core.api.cmd.GroupBy;
import db.sql.core.api.cmd.Having;
import db.sql.core.api.cmd.Join;
import db.sql.core.api.cmd.On;
import db.sql.core.api.cmd.OrderBy;
import db.sql.core.api.cmd.Select;
import db.sql.core.api.cmd.Where;
import db.sql.core.api.cmd.*;
import db.sql.core.api.cmd.fun.Count;
import db.sql.core.api.tookit.CmdUtils;
import db.sql.core.api.tookit.SqlConst;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractQuery<SELF extends AbstractQuery, CMD_FACTORY extends CmdFactory> extends BaseExecutor<SELF, CMD_FACTORY>
        implements db.sql.api.executor.Query<SELF, Dataset, TableField, Cmd, Object, ConditionChain, Select, From, Join, On, Where, GroupBy, Having, OrderBy>, Cmd {
    protected static final Cmd SQL_1 = new Cmd() {
        @Override
        public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
            sqlBuilder = sqlBuilder.append(" 1 ");
            return sqlBuilder;
        }

        @Override
        public boolean contain(Cmd cmd) {
            return false;
        }
    };

    protected static final Cmd SQL_ALL = new Cmd() {
        @Override
        public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
            sqlBuilder = sqlBuilder.append(" * ");
            return sqlBuilder;
        }

        @Override
        public boolean contain(Cmd cmd) {
            return false;
        }
    };

    protected Select select;

    protected From from;

    protected Where where;

    protected Joins joins;

    protected GroupBy groupBy;

    protected Having having;

    protected OrderBy orderBy;

    protected Limit limit;

    protected Unions unions;

    protected final ConditionFaction conditionFaction;

    protected final CMD_FACTORY $;

    public AbstractQuery(CMD_FACTORY $) {
        this.$ = $;
        this.conditionFaction = new ConditionFaction($);
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
        cmdSorts.put(From.class, ++i);
        cmdSorts.put(Joins.class, ++i);
        cmdSorts.put(Where.class, ++i);
        cmdSorts.put(GroupBy.class, ++i);
        cmdSorts.put(Having.class, ++i);
        cmdSorts.put(OrderBy.class, ++i);
        cmdSorts.put(Unions.class, ++i);
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
    public SELF select(Class entity, int storey) {
        return this.select(this.$.all(this.$.table(entity, storey)));
    }

    @Override
    public SELF select1() {
        $select().select(SQL_1);
        return (SELF) this;
    }

    @Override
    public SELF selectAll() {
        $select().select(SQL_ALL);
        return (SELF) this;
    }

    @Override
    public SELF selectCount1() {
        $select().select(Count1.INSTANCE);
        return (SELF) this;
    }

    @Override
    public SELF selectCountAll() {
        $select().select(CountAll.INSTANCE);
        return (SELF) this;
    }

    @Override
    public SELF selectDistinct() {
        $select().distinct();
        return (SELF) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> SELF select(Getter<T> column, int storey, Function<TableField, Cmd> f) {
        TableField field = this.$.field(column, storey);
        if (f != null) {
            return this.select(f.apply(field));
        } else {
            return this.select(field);
        }
    }

    @Override
    public From $from(Dataset... tables) {
        if (this.from == null) {
            from = new From();
            this.append(from);
        }
        this.from.append(tables);
        return from;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SELF from(Class entity, int storey, Consumer<Dataset> consumer) {
        Table table = this.$.table(entity, storey);
        this.from(table);
        return (SELF) this;
    }

    @Override
    public Join $join(JoinMode mode, Dataset mainTable, Dataset secondTable) {
        Join join = new Join(this.conditionFaction, mode, mainTable, secondTable);
        if (Objects.isNull(joins)) {
            joins = new Joins();
            this.append(joins);
        }
        joins.add(join);
        return join;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SELF join(JoinMode mode, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<On> consumer) {
        return this.join(mode, this.$.table(mainTable, mainTableStorey), this.$.table(secondTable, secondTableStorey), consumer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public SELF join(JoinMode mode, Class mainTable, int mainTableStorey, Dataset secondTable, Consumer<On> consumer) {
        return this.join(mode, this.$.table(mainTable, mainTableStorey), secondTable, consumer);
    }

    @Override
    public Where $where() {
        if (where == null) {
            where = new Where(this.conditionFaction);
            this.append(where);
        }
        return where;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SELF join(JoinMode mode, Dataset mainTable, Dataset secondTable, Consumer<On> consumer) {
        Join join = $join(mode, mainTable, secondTable);
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public <T> SELF havingAnd(Getter<T> getter, Function<TableField, db.sql.api.Condition> f) {
        return this.havingAnd(f.apply($.field(getter)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> SELF havingOr(Getter<T> getter, Function<TableField, db.sql.api.Condition> f) {
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
    @SuppressWarnings("unchecked")
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
    public SELF union(Cmd query) {
        $unions().add(new Union(query));
        return (SELF) this;
    }

    @Override
    public SELF unionAll(Cmd subQuery) {
        $unions().add(new Union(SqlConst.UNION_ALL, subQuery));
        return (SELF) this;
    }

    public Select getSelect() {
        return this.select;
    }

    public From getFrom() {
        return this.from;
    }

    public Joins getJoins() {
        return this.joins;
    }

    public Where getWhere() {
        return this.where;
    }


    public SELF limit(int limit) {
        return limit(0, limit);
    }

    public Limit getLimit() {
        return this.limit;
    }

    public GroupBy getGroupBy() {
        return this.groupBy;
    }

    public OrderBy getOrderBy() {
        return this.orderBy;
    }

    @SuppressWarnings("unchecked")
    public SELF limit(int offset, int limit) {
        if (this.limit == null) {
            this.limit = new Limit(offset, limit);
            this.append(this.limit);
        } else {
            this.limit.set(offset, limit);
        }
        return (SELF) this;
    }


    private static List<Cmd> removeOrderBy(List<Cmd> cmds) {
        return cmds.stream().filter(item -> item.getClass() != OrderBy.class && item.getClass() != Limit.class).collect(Collectors.toList());
    }

    private static boolean isCanRemoveLeftJoin(Join current, List<Join> joinList, Select select, Where where, GroupBy groupBy) {
        if (select.isDistinct() && select.contain(current.getSecondTable())) {
            //包含在distinct中 不行
            return false;
        } else if (where != null && where.contain(current.getSecondTable())) {
            //包含在where中条件
            return false;
        } else if (groupBy != null && groupBy.contain(current.getSecondTable())) {
            //包含在groupBy中条件
            return false;
        }
        for (Join j : joinList) {
            if (j == current) {
                continue;
            }
            if (j.getOn().contain(current.getSecondTable())) {
                return false;
            }
        }
        return true;
    }

    private static boolean removeLeftJoin(List<Join> joinList, Select select, Where where, GroupBy groupBy) {
        boolean hasLeftJoin = false;

        for (Join join : joinList) {
            if (join.getMode() == JoinMode.LEFT) {
                hasLeftJoin = true;
                break;
            }
        }

        if (!hasLeftJoin) {
            //未包含left join 不优化
            return false;
        }

        //循环中是否优化过
        boolean removeOne = false;

        int size = joinList.size();
        while (true) {
            boolean remove = false;
            for (int i = size - 1; i >= 0; i--) {
                //从后面扫描 因为从习惯上 最后一个是最容易被优化的
                Join join = joinList.get(i);
                if (join.getMode() == JoinMode.LEFT) {
                    //判断是否能删除
                    if (isCanRemoveLeftJoin(join, joinList, select, where, groupBy)) {
                        removeOne = true;
                        remove = true;
                        joinList.remove(i);
                        --size;
                        break;
                    }
                }
            }
            if (!remove) {
                //假如一个都没有优化，说明无法再优化了
                break;
            }
        }
        return removeOne;
    }

    /**
     * 删除order by
     * 删除join
     * 替换 select 多字段 为 select 1
     *
     * @param cmds
     * @param context
     * @param sqlBuilder
     * @param optimize
     * @return
     */
    private static List<Cmd> getOptimizeCountCmdList(List<Cmd> cmds, SqlBuilderContext context, StringBuilder sqlBuilder, boolean optimize) {
        //移除order by
        cmds = removeOrderBy(cmds);

        if (!optimize) {
            return cmds;
        }

        //用于后续替换
        int selectIndex = -1;
        int joinsIndex = -1;

        //删选组件
        Select select = null;
        Joins joins = null;
        GroupBy groupBy = null;
        Where where = null;
        Unions unions = null;

        Iterator<Cmd> cmdIterator = cmds.iterator();
        int index = -1;
        while (cmdIterator.hasNext()) {
            Cmd cmd = cmdIterator.next();
            Class c = cmd.getClass();
            index++;
            if (c == Select.class) {
                select = (Select) cmd;
                selectIndex = index;

            } else if (c == Joins.class) {
                joins = (Joins) cmd;
                joinsIndex = index;
            } else if (c == GroupBy.class) {
                groupBy = (GroupBy) cmd;
            } else if (c == Unions.class) {
                unions = (Unions) cmd;
            } else if (c == Where.class) {
                where = (Where) cmd;
            }
        }

        if (Objects.nonNull(joins)) {
            List<Join> joinList = new ArrayList<>(joins.getJoins());
            if (removeLeftJoin(joinList, select, where, groupBy)) {
                if (joinList.isEmpty()) {
                    cmds.remove(joins);
                } else {
                    cmds.set(joinsIndex, new Joins(joinList));
                }
            }
        }

        if (Objects.isNull(unions) && !select.isDistinct()) {
            Select newSelect = new Select().select(SQL_1);
            cmds.set(selectIndex, newSelect);
        }
        return cmds;
    }

    @Override
    public StringBuilder countSqlFromQuery(SqlBuilderContext context, StringBuilder sqlBuilder, boolean optimize) {
        List<Cmd> cmdList = getOptimizeCountCmdList(new ArrayList<>(this.sortedCmds()), context, sqlBuilder, optimize);
        int size = cmdList.size();
        boolean needWarp = false;
        for (int i = 0; i < size; i++) {
            Cmd cmd = cmdList.get(i);
            if (cmd instanceof Select) {
                Select select = (Select) cmd;
                if (Objects.nonNull(this.unions)) {
                    needWarp = true;
                } else if (Objects.nonNull(this.groupBy)) {
                    needWarp = true;
                }

                if (!needWarp) {
                    Select newSelect = new Select();
                    if (select.isDistinct()) {
                        newSelect.select(new Count(select));
                    } else {
                        newSelect.select(CountAll.INSTANCE);
                    }
                    cmdList.set(i, newSelect);
                }
                break;
            }
        }

        if (Objects.nonNull(this.unions)) {
            List<Union> unionList = this.unions.getUnions();
            cmdList.remove(this.unions);
            for (Union union : unionList) {
                if (union.getUnionCmd() instanceof AbstractQuery) {
                    AbstractQuery abstractQuery = (AbstractQuery) union.getUnionCmd();
                    cmdList.add(new CmdList(union.getOperator(), getOptimizeCountCmdList(abstractQuery.sortedCmds(), context, sqlBuilder, false)));
                } else {
                    cmdList.add(union);
                }
            }
        }

        if (needWarp) {
            return new StringBuilder("SELECT COUNT(*) FROM (").append(CmdUtils.join(null, context, sqlBuilder, cmdList)).append(") AS T");
        }
        return CmdUtils.join(null, context, sqlBuilder, cmdList);
    }
}

