package db.sql.api.cmd.struct;


import db.sql.api.Getter;
import db.sql.api.cmd.basic.Condition;
import db.sql.api.cmd.executor.Query;
import db.sql.api.cmd.executor.method.condition.ConditionMethods;
import db.sql.api.cmd.executor.method.condition.InConditionMethod;
import db.sql.api.cmd.executor.method.condition.compare.Compare;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;


public interface ConditionChain<SELF extends ConditionChain,
        COLUMN,
        V>

        extends ConditionMethods<SELF, COLUMN, V>,
        Nested<SELF, SELF>,
        Condition {

    boolean hasContent();

    SELF newInstance();

    SELF and();

    SELF or();

    SELF and(Condition condition, boolean when);

    SELF or(Condition condition, boolean when);

    default SELF andNested(Consumer<SELF> consumer) {
        SELF newSelf = newInstance();
        this.and(newSelf, true);
        consumer.accept(newSelf);
        return (SELF) this;
    }

    default SELF orNested(Consumer<SELF> consumer) {
        SELF newSelf = newInstance();
        this.or(newSelf, true);
        consumer.accept(newSelf);
        return (SELF) this;
    }

    default SELF exists(Query query) {
        return this.exists(query, true);
    }

    SELF exists(Query query, boolean when);

    default SELF notExists(Query query) {
        return this.notExists(query, true);
    }

    SELF notExists(Query query, boolean when);

}
