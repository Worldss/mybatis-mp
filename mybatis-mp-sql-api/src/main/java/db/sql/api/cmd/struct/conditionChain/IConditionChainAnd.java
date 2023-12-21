package db.sql.api.cmd.struct.conditionChain;

import db.sql.api.Getter;
import db.sql.api.cmd.GetterField;
import db.sql.api.cmd.basic.ICondition;

import java.util.function.Function;

public interface IConditionChainAnd<SELF extends IConditionChainAnd, TABLE_FIELD> {

    SELF and();

    SELF and(ICondition condition);

    default SELF and(ICondition condition, boolean when){
        if(!when){
            return (SELF)this;
        }
        return this.and(condition);
    }

    default <T> SELF and(Getter<T> column, Function<TABLE_FIELD, ICondition> function) {
        return this.and(true, column, function);
    }

    default <T> SELF and(boolean when, Getter<T> column, Function<TABLE_FIELD, ICondition> function) {
        return this.and(when, column, 1, function);
    }

    default <T> SELF and(Getter<T> column, int storey, Function<TABLE_FIELD, ICondition> function) {
        return this.and(true, column, storey, function);
    }

    <T> SELF and(boolean when, Getter<T> column, int storey, Function<TABLE_FIELD, ICondition> function);

    default <T> SELF and(Function<TABLE_FIELD[], ICondition> function, Getter<T>... columns) {
        return this.and(true, function, columns);
    }

    default <T> SELF and(boolean when, Function<TABLE_FIELD[], ICondition> function, Getter<T>... columns) {
        return this.and(when, function, 1, columns);
    }

    default <T> SELF and(Function<TABLE_FIELD[], ICondition> function, int storey, Getter<T>... columns) {
        return this.and(true, function, storey, columns);
    }

    <T> SELF and(boolean when, Function<TABLE_FIELD[], ICondition> function, int storey, Getter<T>... columns);

    default SELF and(Function<TABLE_FIELD[], ICondition> function, GetterField... getterFields) {
        return and(true, function, getterFields);
    }

    SELF and(boolean when, Function<TABLE_FIELD[], ICondition> function, GetterField... getterFields);
}
