package db.sql.core.api.cmd.struct.query;

import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.Cmd;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.cmd.CmdFactory;
import db.sql.core.api.cmd.basic.Condition;
import db.sql.core.api.cmd.basic.Connector;
import db.sql.core.api.cmd.struct.ConditionBlock;
import db.sql.core.api.cmd.struct.ConditionChain;
import db.sql.core.api.tookit.SqlConst;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Having implements db.sql.api.cmd.struct.query.Having<Having>, Cmd {

    private final CmdFactory cmdFactory;

    private final List<ConditionBlock> conditionBlocks = new ArrayList<>();

    public Having(CmdFactory cmdFactory) {
        this.cmdFactory = cmdFactory;
    }

    public Having and(Function<CmdFactory, Condition> f) {
        conditionBlocks.add(new ConditionBlock(Connector.AND, f.apply(this.cmdFactory)));
        return this;
    }

    @Override
    public Having and(db.sql.api.cmd.basic.Condition condition) {
        conditionBlocks.add(new ConditionBlock(Connector.AND, condition));
        return this;
    }

    public Having or(Function<CmdFactory, Condition> f) {
        conditionBlocks.add(new ConditionBlock(Connector.OR, f.apply(this.cmdFactory)));
        return this;
    }

    @Override
    public Having or(db.sql.api.cmd.basic.Condition condition) {
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
            conditionBlock.getCondition().sql(user, context, sqlBuilder);
            isFirst = false;
        }
        return sqlBuilder;
    }

    @Override
    public boolean contain(Cmd cmd) {
        return CmdUtils.contain(cmd, this.conditionBlocks);
    }
}
