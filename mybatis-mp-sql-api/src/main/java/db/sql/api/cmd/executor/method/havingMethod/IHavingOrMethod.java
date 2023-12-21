package db.sql.api.cmd.executor.method.havingMethod;

import db.sql.api.Getter;
import db.sql.api.cmd.GetterColumnField;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.executor.method.IHavingMethod;

import java.util.function.Function;

public interface IHavingOrMethod<SELF extends IHavingMethod, TABLE_FIELD> {

    SELF havingOr(ICondition condition);

    default SELF havingOr(ICondition condition, boolean when) {
        if (!when) {
            return (SELF) this;
        }
        return this.havingOr(condition);
    }

    default <T> SELF havingOr(Getter<T> column, Function<TABLE_FIELD, ICondition> f) {
        return this.havingOr(true, column, f);
    }

    default <T> SELF havingOr(boolean when, Getter<T> column, Function<TABLE_FIELD, ICondition> f) {
        return this.havingOr(when, column, 1, f);
    }

    default <T> SELF havingOr(Getter<T> column, int storey, Function<TABLE_FIELD, ICondition> f) {
        return this.havingOr(true, column, storey, f);
    }

    <T> SELF havingOr(boolean when, Getter<T> column, int storey, Function<TABLE_FIELD, ICondition> f);

    default <T> SELF havingOr(Function<TABLE_FIELD[], ICondition> f, Getter<T>... columns) {
        return this.havingOr(true, f, columns);
    }

    default <T> SELF havingOr(boolean when, Function<TABLE_FIELD[], ICondition> f, Getter<T>... columns) {
        return this.havingOr(when, f, 1, columns);
    }

    default <T> SELF havingOr(Function<TABLE_FIELD[], ICondition> f, int storey, Getter<T>... columns) {
        return this.havingOr(true, f, storey, columns);
    }

    <T> SELF havingOr(boolean when, Function<TABLE_FIELD[], ICondition> f, int storey, Getter<T>... columns);

    default SELF havingOr(Function<TABLE_FIELD[], ICondition> f, GetterColumnField... getterColumnFields) {
        return this.havingOr(true, f, getterColumnFields);
    }

    SELF havingOr(boolean when, Function<TABLE_FIELD[], ICondition> f, GetterColumnField... getterColumnFields);

}
