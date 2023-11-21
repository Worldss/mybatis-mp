package db.sql.api.cmd.struct;


import db.sql.api.Getter;
import db.sql.api.cmd.Cmd;
import db.sql.api.cmd.basic.Condition;
import db.sql.api.cmd.executor.Query;
import db.sql.api.cmd.executor.method.compare.Compare;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;


public interface ConditionChain<SELF extends ConditionChain, COLUMN, V> extends Compare<SELF, COLUMN, V>, Nested<SELF, SELF>, Condition {

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

    default SELF in(COLUMN column, Serializable... values) {
        return this.in(column, true, values);
    }

    SELF in(COLUMN column, boolean when, Serializable... values);

    default <T> SELF in(Getter<T> column, Serializable... values) {
        return this.in(column, true, values);
    }

    <T> SELF in(Getter<T> column, boolean when, Serializable... values);

    default SELF in(COLUMN column, List<Object> values) {
        return this.in(column, true, values);
    }

    SELF in(COLUMN column, boolean when, List<Object> values);

    default <T> SELF in(Getter<T> column, List<Object> values) {
        return this.in(column, true, values);
    }

    <T> SELF in(Getter<T> column, boolean when, List<Object> values);

    default SELF exists(Query query) {
        return this.exists(query, true);
    }

    SELF exists(Query query, boolean when);
}
