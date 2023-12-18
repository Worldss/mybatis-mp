package db.sql.api.cmd.struct;


import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.executor.method.condition.IConditionMethods;

import java.util.function.Consumer;


public interface IConditionChain<SELF extends IConditionChain,
        COLUMN,
        V>

        extends IConditionMethods<SELF, COLUMN, V>,
        Nested<SELF, SELF>,
        ICondition {

    default boolean isIgnoreEmpty() {
        return false;
    }

    SELF setIgnoreEmpty(boolean bool);

    default boolean isIgnoreNull() {
        return false;
    }

    SELF setIgnoreNull(boolean bool);

    default boolean isStringTrim() {
        return false;
    }

    SELF setStringTrim(boolean bool);


    boolean hasContent();

    SELF newInstance();

    SELF and();

    SELF or();

    SELF and(ICondition condition, boolean when);

    SELF or(ICondition condition, boolean when);

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
