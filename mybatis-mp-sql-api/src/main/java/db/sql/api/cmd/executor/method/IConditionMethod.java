package db.sql.api.cmd.executor.method;

import db.sql.api.Getter;
import db.sql.api.cmd.GetterField;
import db.sql.api.cmd.LikeMode;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.executor.IQuery;
import db.sql.api.cmd.executor.method.condition.IConditionMethods;
import db.sql.api.cmd.struct.Nested;
import db.sql.api.cmd.struct.conditionChain.IConditionChain;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


public interface IConditionMethod<SELF extends IConditionMethod,
        TABLE_FIELD,
        COLUMN,
        V,
        CONDITION_CHAIN extends IConditionChain<CONDITION_CHAIN, TABLE_FIELD, COLUMN, V>
        >
        extends IConditionMethods<SELF, COLUMN, V>,
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


    default <T> SELF and(Getter<T> column, Function<TABLE_FIELD, ICondition> function) {
        return this.and(column, 1, function);
    }

    default <T> SELF and(Getter<T> column, int storey, Function<TABLE_FIELD, ICondition> function) {
        conditionChain().and(column, storey, function);
        return (SELF) this;
    }

    default <T> SELF or(Getter<T> column, Function<TABLE_FIELD, ICondition> function) {
        return this.or(column, 1, function);
    }

    default <T> SELF or(Getter<T> column, int storey, Function<TABLE_FIELD, ICondition> function) {
        conditionChain().or(column, storey, function);
        return (SELF) this;
    }

    default <T> SELF and(Function<TABLE_FIELD[], ICondition> function, Getter<T>... columns) {
        return this.and(function, 1, columns);
    }

    default <T> SELF and(Function<TABLE_FIELD[], ICondition> function, int storey, Getter<T>... columns) {
        conditionChain().and(function, storey, columns);
        return (SELF) this;
    }

    default <T> SELF or(Function<TABLE_FIELD[], ICondition> function, Getter<T>... columns) {
        return this.or(function, 1, columns);
    }

    default <T> SELF or(Function<TABLE_FIELD[], ICondition> function, int storey, Getter<T>... columns) {
        conditionChain().or(function, storey, columns);
        return (SELF) this;
    }

    default SELF and(Function<TABLE_FIELD[], ICondition> function, GetterField... getterFields) {
        conditionChain().and(function, getterFields);
        return (SELF) this;
    }

    default SELF or(Function<TABLE_FIELD[], ICondition> function, GetterField... getterFields) {
        conditionChain().or(function, getterFields);
        return (SELF) this;
    }

    default SELF and(boolean when, Function<TABLE_FIELD[], ICondition> function, GetterField... getterFields) {
        conditionChain().and(when, function, getterFields);
        return (SELF) this;
    }

    default SELF or(boolean when, Function<TABLE_FIELD[], ICondition> function, GetterField... getterFields) {
        conditionChain().or(when, function, getterFields);
        return (SELF) this;
    }

    default SELF and(Function<SELF, ICondition> function) {
        conditionChain().and(function.apply((SELF) this));
        return (SELF) this;
    }

    default SELF or(Function<SELF, ICondition> function) {
        conditionChain().or(function.apply((SELF) this));
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
    default SELF in(COLUMN column, boolean when, IQuery query) {
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
    default <T> SELF in(Getter<T> column, int storey, boolean when, IQuery query) {
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
    default SELF exists(boolean when, IQuery query) {
        conditionChain().exists(when, query);
        return (SELF) this;
    }

    @Override
    default SELF notExists(boolean when, IQuery query) {
        conditionChain().notExists(when, query);
        return (SELF) this;
    }
}
