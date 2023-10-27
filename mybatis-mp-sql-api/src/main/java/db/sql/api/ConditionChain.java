package db.sql.api;


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
}
