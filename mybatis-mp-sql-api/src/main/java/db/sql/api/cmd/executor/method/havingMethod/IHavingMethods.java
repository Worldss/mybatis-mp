package db.sql.api.cmd.executor.method.havingMethod;

import db.sql.api.Getter;
import db.sql.api.cmd.GetterColumnField;
import db.sql.api.cmd.IColumnField;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.executor.ISubQuery;
import db.sql.api.cmd.executor.method.IHavingMethod;

import java.util.function.Function;

public interface IHavingMethods<SELF extends IHavingMethod, TABLE_FIELD, DATASET_FILED>
        extends IHavingAndMethod<SELF, TABLE_FIELD>,
        IHavingOrMethod<SELF, TABLE_FIELD>,
        IHavingSubQueryAndMethod<SELF, DATASET_FILED>,
        IHavingSubQueryOrMethod<SELF, DATASET_FILED> {

    default SELF having(ICondition condition) {
        return this.havingAnd(condition);
    }

    default SELF having(ICondition condition, boolean when) {
        return this.havingAnd(condition, when);
    }

    default <T> SELF having(Getter<T> column, Function<TABLE_FIELD, ICondition> f) {
        return this.havingAnd(column, f);
    }

    default <T> SELF having(boolean when, Getter<T> column, Function<TABLE_FIELD, ICondition> f) {
        return this.havingAnd(when, column, f);
    }

    default <T> SELF having(Getter<T> column, int storey, Function<TABLE_FIELD, ICondition> f) {
        return this.havingAnd(column, storey, f);
    }

    default <T> SELF having(boolean when, Getter<T> column, int storey, Function<TABLE_FIELD, ICondition> f) {
        return this.havingAnd(when, column, storey, f);
    }

    default <T> SELF having(Function<TABLE_FIELD[], ICondition> f, Getter<T>... columns) {
        return this.havingAnd(f, columns);
    }

    default <T> SELF having(boolean when, Function<TABLE_FIELD[], ICondition> f, Getter<T>... columns) {
        return this.havingAnd(when, f, columns);
    }

    default <T> SELF having(Function<TABLE_FIELD[], ICondition> f, int storey, Getter<T>... columns) {
        return this.havingAnd(f, storey, columns);
    }

    default <T> SELF having(boolean when, Function<TABLE_FIELD[], ICondition> f, int storey, Getter<T>... columns) {
        return this.havingAnd(when, f, storey, columns);
    }

    default SELF having(Function<TABLE_FIELD[], ICondition> f, GetterColumnField... getterColumnFields) {
        return this.havingAnd(f, getterColumnFields);
    }

    default SELF having(boolean when, Function<TABLE_FIELD[], ICondition> f, GetterColumnField... getterColumnFields) {
        return this.havingAnd(when, f, getterColumnFields);
    }


    default SELF having(ISubQuery subQuery, String columnName, Function<DATASET_FILED, ICondition> f) {
        return this.havingAnd(subQuery, columnName, f);
    }

    default SELF having(ISubQuery subQuery, boolean when, String columnName, Function<DATASET_FILED, ICondition> f) {
        return this.havingAnd(subQuery, when, columnName, f);
    }

    default <T> SELF having(ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, ICondition> f) {
        return this.havingAnd(subQuery, column, f);
    }

    default <T> SELF having(ISubQuery subQuery, boolean when, Getter<T> column, Function<DATASET_FILED, ICondition> f) {
        return this.havingAnd(subQuery, when, column, f);
    }

    default <T> SELF having(ISubQuery subQuery, Function<DATASET_FILED[], ICondition> f, Getter<T>... columns) {
        return this.havingAnd(subQuery, f, columns);
    }

    default <T> SELF having(ISubQuery subQuery, boolean when, Function<DATASET_FILED[], ICondition> f, Getter<T>... columns) {
        return this.havingAnd(subQuery, when, f, columns);
    }

    default SELF having(ISubQuery subQuery, Function<DATASET_FILED[], ICondition> f, IColumnField... columnFields) {
        return this.havingAnd(subQuery, f, columnFields);
    }

    default SELF havingOr(ISubQuery subQuery, boolean when, Function<DATASET_FILED[], ICondition> f, IColumnField... columnFields) {
        return this.havingAnd(subQuery, when, f, columnFields);
    }
}
