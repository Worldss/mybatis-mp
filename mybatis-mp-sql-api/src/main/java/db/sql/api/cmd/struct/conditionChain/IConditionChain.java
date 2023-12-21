package db.sql.api.cmd.struct.conditionChain;


import db.sql.api.Getter;
import db.sql.api.cmd.GetterField;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.executor.method.condition.IConditionMethods;
import db.sql.api.cmd.struct.Nested;

import java.util.function.Consumer;
import java.util.function.Function;


public interface IConditionChain<SELF extends IConditionChain,
        TABLE_FIELD,
        COLUMN,
        V>

        extends IConditionMethods<SELF, COLUMN, V>,
        Nested<SELF, SELF>,
        IConditionChainAnd<SELF,TABLE_FIELD>,
        IConditionChainOr<SELF,TABLE_FIELD>,
        ICondition {

    SELF setIgnoreEmpty(boolean bool);

    SELF setIgnoreNull(boolean bool);

    SELF setStringTrim(boolean bool);

    boolean hasContent();

    SELF newInstance();

    @Override
    default SELF andNested(Consumer<SELF> consumer) {
        SELF newSelf = newInstance();
        this.and(newSelf, true);
        consumer.accept(newSelf);
        return (SELF) this;
    }

    @Override
    default SELF orNested(Consumer<SELF> consumer) {
        SELF newSelf = newInstance();
        this.or(newSelf, true);
        consumer.accept(newSelf);
        return (SELF) this;
    }
}
