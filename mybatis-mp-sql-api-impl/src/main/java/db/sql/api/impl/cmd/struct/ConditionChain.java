package db.sql.api.impl.cmd.struct;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.LikeMode;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.executor.IQuery;
import db.sql.api.cmd.struct.IConditionChain;
import db.sql.api.impl.cmd.ConditionFactory;
import db.sql.api.impl.cmd.basic.ConditionBlock;
import db.sql.api.impl.cmd.basic.Connector;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConditionChain implements IConditionChain<ConditionChain, Cmd, Object>, ICondition {

    private final ConditionFactory conditionFactory;

    private final ConditionChain parent;
    private List<ConditionBlock> conditionBlocks;
    private Connector connector = Connector.AND;

    public ConditionChain(ConditionFactory conditionFactory) {
        this(conditionFactory, null);
    }

    public ConditionChain(ConditionFactory conditionFactory, ConditionChain parent) {
        this.conditionFactory = conditionFactory;
        this.parent = parent;
    }

    @Override
    public boolean hasContent() {
        return conditionBlocks != null && !conditionBlocks.isEmpty();
    }

    @Override
    public ConditionChain newInstance() {
        return new ConditionChain(conditionFactory, this);
    }

    private List<ConditionBlock> conditionBlocks() {
        if (conditionBlocks == null) {
            conditionBlocks = new ArrayList<>();
        }
        return this.conditionBlocks;
    }

    @Override
    public ConditionChain and() {
        this.connector = Connector.AND;
        return this;
    }

    @Override
    public ConditionChain or() {
        this.connector = Connector.OR;
        return this;
    }

    @Override
    public ConditionChain and(ICondition condition, boolean when) {
        this.and();
        if (when && condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain or(ICondition condition, boolean when) {
        this.or();
        if (when && condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain empty(Getter<T> column, int storey, boolean when) {
        ICondition condition = conditionFactory.empty(column, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain empty(Cmd column, boolean when) {
        ICondition condition = conditionFactory.empty(column, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain notEmpty(Getter<T> column, int storey, boolean when) {
        ICondition condition = conditionFactory.notEmpty(column, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain notEmpty(Cmd column, boolean when) {
        ICondition condition = conditionFactory.notEmpty(column, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain eq(Cmd column, Object value, boolean when) {
        ICondition condition = conditionFactory.eq(column, value, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain ne(Cmd column, Object value, boolean when) {
        ICondition condition = conditionFactory.ne(column, value, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain gt(Cmd column, Object value, boolean when) {
        ICondition condition = conditionFactory.gt(column, value, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain gte(Cmd column, Object value, boolean when) {
        ICondition condition = conditionFactory.gte(column, value, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain lt(Cmd column, Object value, boolean when) {
        ICondition condition = conditionFactory.lt(column, value, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain lte(Cmd column, Object value, boolean when) {
        ICondition condition = conditionFactory.lte(column, value, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain between(Cmd column, Serializable value, Serializable value2, boolean when) {
        ICondition condition = conditionFactory.between(column, value, value2, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain notBetween(Cmd column, Serializable value, Serializable value2, boolean when) {
        ICondition condition = conditionFactory.notBetween(column, value, value2, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain isNull(Cmd column, boolean when) {
        ICondition condition = conditionFactory.isNull(column, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain isNotNull(Cmd column, boolean when) {
        ICondition condition = conditionFactory.isNotNull(column, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain like(Cmd column, String value, LikeMode mode, boolean when) {
        ICondition condition = conditionFactory.like(column, value, mode, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain notLike(Cmd column, String value, LikeMode mode, boolean when) {
        ICondition condition = conditionFactory.notLike(column, value, mode, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain between(Getter<T> column, Serializable value, Serializable value2, int storey, boolean when) {
        ICondition condition = conditionFactory.between(column, value, value2, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain eq(Getter<T> column, Object value, int storey, boolean when) {
        ICondition condition = conditionFactory.eq(column, value, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T, T2> ConditionChain eq(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        ICondition condition = conditionFactory.eq(column, columnStorey, value, valueStorey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain gt(Getter<T> column, Object value, int storey, boolean when) {
        ICondition condition = conditionFactory.gt(column, value, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T, T2> ConditionChain gt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        ICondition condition = conditionFactory.gt(column, columnStorey, value, valueStorey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain gte(Getter<T> column, Object value, int storey, boolean when) {
        ICondition condition = conditionFactory.gte(column, value, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T, T2> ConditionChain gte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        ICondition condition = conditionFactory.gte(column, columnStorey, value, valueStorey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain like(Getter<T> column, String value, LikeMode mode, int storey, boolean when) {
        ICondition condition = conditionFactory.like(column, value, mode, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain lt(Getter<T> column, Object value, int storey, boolean when) {
        ICondition condition = conditionFactory.lt(column, value, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T, T2> ConditionChain lt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        ICondition condition = conditionFactory.lt(column, columnStorey, value, valueStorey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain lte(Getter<T> column, Object value, int storey, boolean when) {
        ICondition condition = conditionFactory.lte(column, value, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T, T2> ConditionChain lte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        ICondition condition = conditionFactory.lte(column, columnStorey, value, valueStorey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain ne(Getter<T> column, Object value, int storey, boolean when) {
        ICondition condition = conditionFactory.ne(column, value, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T, T2> ConditionChain ne(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        ICondition condition = conditionFactory.ne(column, columnStorey, value, valueStorey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain notBetween(Getter<T> column, Serializable value, Serializable value2, int storey, boolean when) {
        ICondition condition = conditionFactory.notBetween(column, value, value2, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain notLike(Getter<T> column, String value, LikeMode mode, int storey, boolean when) {
        ICondition condition = conditionFactory.notLike(column, value, mode, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain isNotNull(Getter<T> column, int storey, boolean when) {
        ICondition condition = conditionFactory.isNotNull(column, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain isNull(Getter<T> column, int storey, boolean when) {
        ICondition condition = conditionFactory.isNull(column, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain in(Cmd cmd, boolean when, IQuery query) {
        ICondition condition = conditionFactory.in(cmd, when, query);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain in(Cmd cmd, boolean when, Serializable... values) {
        ICondition condition = conditionFactory.in(cmd, values, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain in(Cmd cmd, boolean when, List<Serializable> values) {
        ICondition condition = conditionFactory.in(cmd, when, values);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain in(Getter<T> column, int storey, boolean when, IQuery query) {
        ICondition condition = conditionFactory.in(column, storey, when, query);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain in(Getter<T> column, int storey, boolean when, Serializable... values) {
        ICondition condition = conditionFactory.in(column, storey, when, values);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain in(Getter<T> column, int storey, boolean when, List<Serializable> values) {
        ICondition condition = conditionFactory.in(column, storey, when, values);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain exists(boolean when, IQuery query) {
        ICondition condition = conditionFactory.exists(when, query);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain notExists(boolean when, IQuery query) {
        ICondition condition = conditionFactory.notExists(when, query);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (conditionBlocks == null || conditionBlocks.isEmpty()) {
            return sqlBuilder;
        }
        if ((!(parent instanceof Where) && !(parent instanceof On)) || this.parent != null) {
            sqlBuilder = sqlBuilder.append(SqlConst.BLANK).append(SqlConst.BRACKET_LEFT);
        }
        boolean isFirst = true;
        for (ConditionBlock conditionBlock : this.conditionBlocks) {
            if (conditionBlock.getCondition() instanceof ConditionChain) {
                ConditionChain conditionChain = (ConditionChain) conditionBlock.getCondition();
                if (!conditionChain.hasContent()) {
                    continue;
                }
            }
            if (!isFirst) {
                sqlBuilder = sqlBuilder.append(SqlConst.BLANK).append(conditionBlock.getConnector()).append(SqlConst.BLANK);
            }
            conditionBlock.getCondition().sql(module, this, context, sqlBuilder);
            isFirst = false;
        }
        if ((!(parent instanceof Where) && !(parent instanceof On)) || this.parent != null) {
            sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT).append(SqlConst.BLANK);
        }

        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.conditionBlocks);
    }
}
