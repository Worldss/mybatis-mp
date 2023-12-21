package db.sql.api.cmd.struct.conditionChain;

import db.sql.api.Getter;
import db.sql.api.cmd.GetterField;
import db.sql.api.cmd.basic.ICondition;

import java.util.function.Function;

public interface IConditionChainOr<SELF extends IConditionChainOr, TABLE_FIELD> {

    SELF or();

    SELF or(ICondition condition);

    default SELF or(ICondition condition, boolean when) {
        if (!when) {
            return (SELF) this;
        }
        return this.or(condition);
    }

    default <T> SELF or(Getter<T> column, Function<TABLE_FIELD, ICondition> function) {
        return this.or(true, column, function);
    }

    default <T> SELF or(boolean when, Getter<T> column, Function<TABLE_FIELD, ICondition> function) {
        return this.or(when, column, 1, function);
    }

    default <T> SELF or(Getter<T> column, int storey, Function<TABLE_FIELD, ICondition> function) {
        return this.or(true, column, storey, function);
    }

    <T> SELF or(boolean when, Getter<T> column, int storey, Function<TABLE_FIELD, ICondition> function);

    default <T> SELF or(Function<TABLE_FIELD[], ICondition> function, Getter<T>... columns) {
        return this.or(true, function, columns);
    }

    default <T> SELF or(boolean when, Function<TABLE_FIELD[], ICondition> function, Getter<T>... columns) {
        return this.or(when, function, 1, columns);
    }

    default <T> SELF or(Function<TABLE_FIELD[], ICondition> function, int storey, Getter<T>... columns) {
        return this.or(true, function, 1, columns);
    }

    <T> SELF or(boolean when, Function<TABLE_FIELD[], ICondition> function, int storey, Getter<T>... columns);

    default SELF or(Function<TABLE_FIELD[], ICondition> function, GetterField... getterFields) {
        return or(true, function, getterFields);
    }

    SELF or(boolean when, Function<TABLE_FIELD[], ICondition> function, GetterField... getterFields);
}
