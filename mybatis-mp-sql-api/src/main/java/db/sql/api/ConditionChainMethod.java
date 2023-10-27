package db.sql.api;

import java.util.function.Consumer;

public interface ConditionChainMethod<SELF extends ConditionChainMethod, COLUMN, V, CONDITION_CHAIN extends ConditionChain<CONDITION_CHAIN, COLUMN, V>> extends Compare<SELF, COLUMN, V>, Nested<SELF, CONDITION_CHAIN> {

    CONDITION_CHAIN conditionChain();

    default SELF and() {
        conditionChain().and();
        return (SELF) this;
    }

    default SELF or() {
        conditionChain().or();
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
    default SELF between(COLUMN column, V value, V value2, boolean when) {
        conditionChain().between(column, value, value2, when);
        return (SELF) this;
    }

    @Override
    default SELF notBetween(COLUMN column, V value, V value2, boolean when) {
        conditionChain().notBetween(column, value, value2, when);
        return (SELF) this;
    }

    @Override
    default SELF like(COLUMN column, V value, LikeMode mode, boolean when) {
        conditionChain().like(column, value, mode, when);
        return (SELF) this;
    }

    @Override
    default SELF notLike(COLUMN column, V value, LikeMode mode, boolean when) {
        conditionChain().notLike(column, value, mode, when);
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
        conditionChain().lt(column, value, storey, when);
        return (SELF) this;
    }

    @Override
    default <T, T2> SELF lte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        conditionChain().lte(column, columnStorey, value, valueStorey, when);
        return (SELF) this;
    }


    @Override
    default <T> SELF like(Getter<T> column, V value, LikeMode mode, int storey, boolean when) {
        conditionChain().like(column, value, mode, storey, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF notLike(Getter<T> column, V value, LikeMode mode, int storey, boolean when) {
        conditionChain().notLike(column, value, mode, storey, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF between(Getter<T> column, V value, V value2, int storey, boolean when) {
        conditionChain().between(column, value, value2, storey, when);
        return (SELF) this;
    }

    @Override
    default <T> SELF notBetween(Getter<T> column, V value, V value2, int storey, boolean when) {
        conditionChain().notBetween(column, value, value2, storey, when);
        return (SELF) this;
    }
}
