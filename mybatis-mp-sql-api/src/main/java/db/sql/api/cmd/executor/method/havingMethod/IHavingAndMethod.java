package db.sql.api.cmd.executor.method.havingMethod;

import db.sql.api.Getter;
import db.sql.api.cmd.GetterColumnField;
import db.sql.api.cmd.basic.ICondition;

import java.util.function.Function;

public interface IHavingAndMethod<SELF extends IHavingAndMethod, TABLE_FIELD> {

    SELF havingAnd(ICondition condition);

    default SELF havingAnd(ICondition condition, boolean when) {
        if (!when) {
            return (SELF) this;
        }
        return this.havingAnd(condition);
    }

    default <T> SELF havingAnd(Getter<T> column, Function<TABLE_FIELD, ICondition> f) {
        return this.havingAnd(true, column, f);
    }

    default <T> SELF havingAnd(boolean when, Getter<T> column, Function<TABLE_FIELD, ICondition> f) {
        return this.havingAnd(when, column, 1, f);
    }

    default <T> SELF havingAnd(Getter<T> column, int storey, Function<TABLE_FIELD, ICondition> f) {
        return this.havingAnd(true, column, storey, f);
    }

    <T> SELF havingAnd(boolean when, Getter<T> column, int storey, Function<TABLE_FIELD, ICondition> f);

    default <T> SELF havingAnd(Function<TABLE_FIELD[], ICondition> f, Getter<T>... columns) {
        return this.havingAnd(true, f, columns);
    }

    default <T> SELF havingAnd(boolean when, Function<TABLE_FIELD[], ICondition> f, Getter<T>... columns) {
        return this.havingAnd(when, f, 1, columns);
    }

    default <T> SELF havingAnd(Function<TABLE_FIELD[], ICondition> f, int storey, Getter<T>... columns) {
        return this.havingAnd(true, f, storey, columns);
    }

    <T> SELF havingAnd(boolean when, Function<TABLE_FIELD[], ICondition> f, int storey, Getter<T>... columns);


    default SELF havingAnd(Function<TABLE_FIELD[], ICondition> f, GetterColumnField... getterColumnFields) {
        return this.havingAnd(true, f, getterColumnFields);
    }

    SELF havingAnd(boolean when, Function<TABLE_FIELD[], ICondition> f, GetterColumnField... getterColumnFields);
}
