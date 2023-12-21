package db.sql.api.cmd.executor.method.havingMethod;

import db.sql.api.Getter;
import db.sql.api.cmd.ColumnField;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.executor.ISubQuery;
import db.sql.api.cmd.executor.method.IHavingMethod;

import java.util.function.Function;

public interface IHavingSubQueryAndMethod<SELF extends IHavingMethod, DATASET_FILED> {

    default SELF havingAnd(ISubQuery subQuery, String columnName, Function<DATASET_FILED, ICondition> f) {
        return this.havingAnd(subQuery, true, columnName, f);
    }

    SELF havingAnd(ISubQuery subQuery, boolean when, String columnName, Function<DATASET_FILED, ICondition> f);

    default <T> SELF havingAnd(ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, ICondition> f) {
        return havingAnd(subQuery, true, column, f);
    }

    <T> SELF havingAnd(ISubQuery subQuery, boolean when, Getter<T> column, Function<DATASET_FILED, ICondition> f);

    default <T> SELF havingAnd(ISubQuery subQuery, Function<DATASET_FILED[], ICondition> f, Getter<T>... columns) {
        return this.havingAnd(subQuery, true, f, columns);
    }

    <T> SELF havingAnd(ISubQuery subQuery, boolean when, Function<DATASET_FILED[], ICondition> f, Getter<T>... columns);

    default SELF havingAnd(ISubQuery subQuery, Function<DATASET_FILED[], ICondition> f, ColumnField... columnFields) {
        return this.havingAnd(subQuery, true, f, columnFields);
    }

    SELF havingAnd(ISubQuery subQuery, boolean when, Function<DATASET_FILED[], ICondition> f, ColumnField... columnFields);

}
