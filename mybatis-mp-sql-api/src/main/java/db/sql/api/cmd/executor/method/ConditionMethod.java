package db.sql.api.cmd.executor.method;

import db.sql.api.Getter;
import db.sql.api.cmd.LikeMode;
import db.sql.api.cmd.basic.Condition;
import db.sql.api.cmd.executor.Query;
import db.sql.api.cmd.executor.method.condition.ConditionMethods;
import db.sql.api.cmd.struct.ConditionChain;
import db.sql.api.cmd.struct.Nested;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


public interface ConditionMethod<SELF extends ConditionMethod,
        COLUMN,
        V,
        CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>
        >
        extends ConditionMethods<SELF, COLUMN, V>,
        Nested<SELF, CONDITION_CHAIN> {

    CONDITION_CHAIN conditionChain();

    default SELF and() {
        conditionChain().and();
        return (SELF) this;
    }

    default SELF or() {
        conditionChain().or();
        return (SELF) this;
    }

    default SELF and(Function<SELF, Condition> function) {
        conditionChain().and(function.apply((SELF) this), true);
        return (SELF) this;
    }

    default SELF or(Function<SELF, Condition> function) {
        conditionChain().or(function.apply((SELF) this), true);
        return (SELF) this;
    }

    @Override
    default SELF andNested(Consumer<CONDITION_CHAIN> consumer) {
        conditionChain().andNested(consumer);
        return (SELF) this;
    }

    @Override
    default SELF orNested(Consumer<CONDITION_CHAIN> consumer) {
        conditionChain().orNested(consumer);
        return (SELF) this;
    }

    @Override
    default SELF eq(COLUMN column, V value, boolean when) {
        conditionChain().eq(column, value, when);
        return (SELF) this;
    }

    @Override
    default SELF ne(COLUMN column, V value, boolean when) {
        conditionChain().ne(column, value, when);
        return (SELF) this;
    }

    @Override
    default SELF gt(COLUMN column, V value, boolean when) {
        conditionChain().gt(column, value, when);
        return (SELF) this;
    }

    @Override
    default SELF gte(COLUMN column, V value, boolean when) {
        conditionChain().gte(column, value, when);
        return (SELF) this;
    }

    @Override
    default SELF lt(COLUMN column, V value, boolean when) {
        conditionChain().lt(column, value, when);
        return (SELF) this;
    }

    @Override
    default SELF lte(COLUMN column, V value, boolean when) {
        conditionChain().lte(column, value, when);
        return (SELF) this;
    }

    @Override
    default SELF between(COLUMN column, Serializable value, Serializable value2, boolean when) {
        conditionChain().between(column, value, value2, when);
        return (SELF) this;
    }

    @Override
    default SELF notBetween(COLUMN column, Serializable value, Serializable value2, boolean when) {
        conditionChain().notBetween(column, value, value2, when);
        return (SELF) this;
    }

    @Override
    default SELF like(COLUMN column, String value, LikeMode mode, boolean when) {
        conditionChain().like(column, value, mode, when);
        return (SELF) this;
    }

    @Override
    default SELF notLike(COLUMN column, String value, LikeMode mode, boolean when) {
        conditionChain().notLike(column, value, mode, when);
        return (SELF) this;
    }

    @Override
    default SELF isNull(COLUMN column, boolean when) {
        conditionChain().isNull(column, when);
        return (SELF) this;
    }

    @Override
    default SELF isNotNull(COLUMN column, boolean when) {
        conditionChain().isNotNull(column, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF empty(Getter<T> column, int storey, boolean when) {
        conditionChain().empty(column, storey, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF notEmpty(Getter<T> column, int storey, boolean when) {
        conditionChain().notEmpty(column, storey, when);
        return (SELF) this;
    }

    @Override
    default SELF empty(COLUMN column, boolean when) {
        conditionChain().empty(column, when);
        return (SELF) this;
    }

    @Override
    default SELF notEmpty(COLUMN column, boolean when) {
        conditionChain().empty(column, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF eq(Getter<T> column, V value, int storey, boolean when) {
        conditionChain().eq(column, value, storey, when);
        return (SELF) this;
    }

    @Override
    default <T, T2> SELF eq(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        conditionChain().eq(column, columnStorey, value, valueStorey, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF ne(Getter<T> column, V value, int storey, boolean when) {
        conditionChain().ne(column, value, storey, when);
        return (SELF) this;
    }

    @Override
    default <T, T2> SELF ne(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        conditionChain().ne(column, columnStorey, value, valueStorey, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF gt(Getter<T> column, V value, int storey, boolean when) {
        conditionChain().gt(column, value, storey, when);
        return (SELF) this;
    }

    @Override
    default <T, T2> SELF gt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        conditionChain().gt(column, columnStorey, value, valueStorey, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF gte(Getter<T> column, V value, int storey, boolean when) {
        conditionChain().gte(column, value, storey, when);
        return (SELF) this;
    }

    @Override
    default <T, T2> SELF gte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        conditionChain().gte(column, columnStorey, value, valueStorey, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF lt(Getter<T> column, V value, int storey, boolean when) {
        conditionChain().lt(column, value, storey, when);
        return (SELF) this;
    }

    @Override
    default <T, T2> SELF lt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        conditionChain().lt(column, columnStorey, value, valueStorey, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF lte(Getter<T> column, V value, int storey, boolean when) {
        conditionChain().lte(column, value, storey, when);
        return (SELF) this;
    }

    @Override
    default <T, T2> SELF lte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        conditionChain().lte(column, columnStorey, value, valueStorey, when);
        return (SELF) this;
    }


    @Override
    default <T> SELF like(Getter<T> column, String value, LikeMode mode, int storey, boolean when) {
        conditionChain().like(column, value, mode, storey, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF notLike(Getter<T> column, String value, LikeMode mode, int storey, boolean when) {
        conditionChain().notLike(column, value, mode, storey, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF between(Getter<T> column, Serializable value, Serializable value2, int storey, boolean when) {
        conditionChain().between(column, value, value2, storey, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF notBetween(Getter<T> column, Serializable value, Serializable value2, int storey, boolean when) {
        conditionChain().notBetween(column, value, value2, storey, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF isNull(Getter<T> column, int storey, boolean when) {
        conditionChain().isNull(column, storey, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF isNotNull(Getter<T> column, int storey, boolean when) {
        conditionChain().isNotNull(column, storey, when);
        return (SELF) this;
    }

    @Override
    default SELF in(COLUMN column, boolean when, Query query) {
        conditionChain().in(column, when, query);
        return (SELF) this;
    }

    @Override
    default SELF in(COLUMN column, boolean when, Serializable... values) {
        conditionChain().in(column, when, values);
        return (SELF) this;
    }

    @Override
    default SELF in(COLUMN column, boolean when, List<Serializable> values) {
        conditionChain().in(column, when, values);
        return (SELF) this;
    }

    @Override
    default <T> SELF in(Getter<T> column, int storey, boolean when, Query query) {
        conditionChain().in(column, storey, when, query);
        return (SELF) this;
    }

    @Override
    default <T> SELF in(Getter<T> column, int storey, boolean when, Serializable... values) {
        conditionChain().in(column, storey, when, values);
        return (SELF) this;
    }

    @Override
    default <T> SELF in(Getter<T> column, int storey, boolean when, List<Serializable> values) {
        conditionChain().in(column, storey, when, values);
        return (SELF) this;
    }

    @Override
    default SELF exists(boolean when, Query query) {
        conditionChain().exists(when, query);
        return (SELF) this;
    }

    @Override
    default SELF notExists(boolean when, Query query) {
        conditionChain().notExists(when, query);
        return (SELF) this;
    }
}
