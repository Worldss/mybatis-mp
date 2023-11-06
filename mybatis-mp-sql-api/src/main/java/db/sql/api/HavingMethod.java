package db.sql.api;

import java.util.function.Consumer;
import java.util.function.Function;

public interface HavingMethod<SELF extends HavingMethod, TABLE_FIELD, COLUMN, V, CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>, HAVING extends Having<HAVING>> {

    default <T> SELF having(Getter<T> getter, Function<TABLE_FIELD, Condition> f) {
        return this.havingAnd(getter, f);
    }

    SELF having(Consumer<HAVING> consumer);

    <T> SELF havingAnd(Getter<T> getter, Function<TABLE_FIELD, Condition> f);

    <T> SELF havingOr(Getter<T> getter, Function<TABLE_FIELD, Condition> f);

    default SELF having(Condition condition) {
        return this.havingAnd(condition);
    }

    SELF havingAnd(Condition condition);

    SELF havingOr(Condition condition);
}
