package db.sql.api.cmd.executor.method;

import db.sql.api.cmd.struct.IConditionChain;
import db.sql.api.cmd.struct.IWhereIgnoreMethod;

public interface IWhereMethod<SELF extends IWhereMethod,
        TABLE_FIELD,
        COLUMN,
        V,
        CONDITION_CHAIN extends IConditionChain<CONDITION_CHAIN, TABLE_FIELD, COLUMN, V>
        >
        extends IConditionMethod<SELF, TABLE_FIELD, COLUMN, V, CONDITION_CHAIN>,
        IWhereIgnoreMethod<SELF> {

    @Override
    default SELF ignoreNullValueInCondition(boolean bool) {
        conditionChain().setIgnoreNull(bool);
        return (SELF) this;
    }

    @Override
    default SELF ignoreEmptyInCondition(boolean bool) {
        conditionChain().setIgnoreEmpty(bool);
        return (SELF) this;
    }

    @Override
    default SELF trimStringInCondition(boolean bool) {
        conditionChain().setStringTrim(bool);
        return (SELF) this;
    }

    /**
     * 为搜索（注意查询和搜索是不一样的）
     *
     * @return
     */
    default SELF forSearch() {
        this.ignoreNullValueInCondition(true);
        this.ignoreEmptyInCondition(true);
        this.trimStringInCondition(true);
        return (SELF) this;
    }

}
