package db.sql.core.api.cmd;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.LikeMode;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.SqlConst;

import java.util.ArrayList;
import java.util.List;

public class ConditionChain implements db.sql.api.ConditionChain<ConditionChain, Cmd, Object>, db.sql.api.Condition, Cmd {

    private final ConditionFaction conditionFaction;

    private final ConditionChain parent;

    public ConditionChain(ConditionFaction conditionFaction) {
        this(conditionFaction, null);
    }

    public ConditionChain(ConditionFaction conditionFaction, ConditionChain parent) {
        this.conditionFaction = conditionFaction;
        this.parent = parent;
    }

    private List<ConditionBlock> conditionBlocks = new ArrayList<>();

    private Connector connector = Connector.AND;

    @Override
    public boolean hasContent() {
        return conditionBlocks != null && !conditionBlocks.isEmpty();
    }

    @Override
    public ConditionChain newInstance() {
        return new ConditionChain(conditionFaction, this);
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
    public ConditionChain and(db.sql.api.Condition condition, boolean when) {
        this.and();
        if (when && condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain or(db.sql.api.Condition condition, boolean when) {
        this.or();
        if (when && condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain eq(Cmd cmd, Object value, boolean when) {
        Condition condition = conditionFaction.eq(cmd, value, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain ne(Cmd cmd, Object value, boolean when) {
        Condition condition = conditionFaction.ne(cmd, value, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain gt(Cmd cmd, Object value, boolean when) {
        Condition condition = conditionFaction.gt(cmd, value, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain gte(Cmd cmd, Object value, boolean when) {
        Condition condition = conditionFaction.gte(cmd, value, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain lt(Cmd cmd, Object value, boolean when) {
        Condition condition = conditionFaction.lt(cmd, value, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain lte(Cmd cmd, Object value, boolean when) {
        Condition condition = conditionFaction.lte(cmd, value, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain between(Cmd cmd, Object value, Object value2, boolean when) {
        Condition condition = conditionFaction.between(cmd, value, value2, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain notBetween(Cmd cmd, Object value, Object value2, boolean when) {
        Condition condition = conditionFaction.notBetween(cmd, value, value2, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain isNull(Cmd cmd, boolean when) {
        Condition condition = conditionFaction.isNull(cmd, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain isNotNull(Cmd cmd, boolean when) {
        Condition condition = conditionFaction.isNotNull(cmd, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain like(Cmd cmd, Object value, LikeMode mode, boolean when) {
        Condition condition = conditionFaction.like(cmd, value, mode, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public ConditionChain notLike(Cmd cmd, Object value, LikeMode mode, boolean when) {
        Condition condition = conditionFaction.notLike(cmd, value, mode, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain between(Getter<T> column, Object value, Object value2, int storey, boolean when) {
        Condition condition = conditionFaction.between(column, value, value2, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain eq(Getter<T> column, Object value, int storey, boolean when) {
        Condition condition = conditionFaction.eq(column, value, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T, T2> ConditionChain eq(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        Condition condition = conditionFaction.eq(column, columnStorey, value, valueStorey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain gt(Getter<T> column, Object value, int storey, boolean when) {
        Condition condition = conditionFaction.gt(column, value, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T, T2> ConditionChain gt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        Condition condition = conditionFaction.gt(column, columnStorey, value, valueStorey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain gte(Getter<T> column, Object value, int storey, boolean when) {
        Condition condition = conditionFaction.gte(column, value, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T, T2> ConditionChain gte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        Condition condition = conditionFaction.gte(column, columnStorey, value, valueStorey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain like(Getter<T> column, Object value, LikeMode mode, int storey, boolean when) {
        Condition condition = conditionFaction.like(column, value, mode, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain lt(Getter<T> column, Object value, int storey, boolean when) {
        Condition condition = conditionFaction.lt(column, value, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T, T2> ConditionChain lt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        Condition condition = conditionFaction.lt(column, columnStorey, value, valueStorey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain lte(Getter<T> column, Object value, int storey, boolean when) {
        Condition condition = conditionFaction.lte(column, value, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T, T2> ConditionChain lte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        Condition condition = conditionFaction.lte(column, columnStorey, value, valueStorey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain ne(Getter<T> column, Object value, int storey, boolean when) {
        Condition condition = conditionFaction.ne(column, value, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T, T2> ConditionChain ne(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        Condition condition = conditionFaction.ne(column, columnStorey, value, valueStorey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain notBetween(Getter<T> column, Object value, Object value2, int storey, boolean when) {
        Condition condition = conditionFaction.notBetween(column, value, value2, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }

    @Override
    public <T> ConditionChain notLike(Getter<T> column, Object value, LikeMode mode, int storey, boolean when) {
        Condition condition = conditionFaction.notLike(column, value, mode, storey, when);
        if (condition != null) {
            conditionBlocks().add(new ConditionBlock(this.connector, condition));
        }
        return this;
    }


    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (conditionBlocks == null || conditionBlocks.isEmpty()) {
            return sqlBuilder;
        }
        if (parent != null) {
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
            ((Cmd) conditionBlock.getCondition()).sql(user, context, sqlBuilder);
            isFirst = false;
        }
        if (parent != null) {
            sqlBuilder = sqlBuilder.append(SqlConst.BRACKET_RIGHT).append(SqlConst.BLANK);
        }

        return sqlBuilder;
    }

    @Override
    public <T> ConditionChain isNotNull(Getter<T> column, int storey, boolean when) {
        return null;
    }

    @Override
    public <T> ConditionChain isNull(Getter<T> column, int storey, boolean when) {
        return null;
    }
}
