package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.CmdUtils;
import db.sql.core.api.tookit.SqlConst;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Having implements db.sql.api.Having<Having>, Cmd {

    private final CmdFactory cmdFactory;

    private List<ConditionBlock> conditionBlocks = new ArrayList<>();

    public Having(CmdFactory cmdFactory) {
        this.cmdFactory = cmdFactory;
    }

    public Having and(Function<CmdFactory, Condition> f) {
        conditionBlocks.add(new ConditionBlock(Connector.AND, f.apply(this.cmdFactory)));
        return this;
    }

    @Override
    public Having and(db.sql.api.Condition condition) {
        conditionBlocks.add(new ConditionBlock(Connector.AND, condition));
        return this;
    }

    public Having or(Function<CmdFactory, Condition> f) {
        conditionBlocks.add(new ConditionBlock(Connector.OR, f.apply(this.cmdFactory)));
        return this;
    }

    @Override
    public Having or(db.sql.api.Condition condition) {
        conditionBlocks.add(new ConditionBlock(Connector.OR, condition));
        return this;
    }


    @Override
    public StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (conditionBlocks == null || conditionBlocks.isEmpty()) {
            return sqlBuilder;
        }
        boolean isFirst = true;

        sqlBuilder = sqlBuilder.append(SqlConst.HAVING);
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
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.conditionBlocks);
    }
}
