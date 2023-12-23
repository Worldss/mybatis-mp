package db.sql.api.cmd.executor.method.havingMethod;

import db.sql.api.Getter;
import db.sql.api.cmd.IColumnField;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.executor.ISubQuery;

import java.util.function.Function;

public interface IHavingSubQueryOrMethod<SELF extends IHavingSubQueryOrMethod, DATASET_FILED> {

    default SELF havingOr(ISubQuery subQuery, String columnName, Function<DATASET_FILED, ICondition> f) {
        return this.havingOr(subQuery, true, columnName, f);
    }

    SELF havingOr(ISubQuery subQuery, boolean when, String columnName, Function<DATASET_FILED, ICondition> f);

    default <T> SELF havingOr(ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, ICondition> f) {
        return havingOr(subQuery, true, column, f);
    }

    <T> SELF havingOr(ISubQuery subQuery, boolean when, Getter<T> column, Function<DATASET_FILED, ICondition> f);


    default <T> SELF havingOr(ISubQuery subQuery, Function<DATASET_FILED[], ICondition> f, Getter<T>... columns) {
        return this.havingOr(subQuery, true, f, columns);
    }

    <T> SELF havingOr(ISubQuery subQuery, boolean when, Function<DATASET_FILED[], ICondition> f, Getter<T>... columns);


    default SELF havingOr(ISubQuery subQuery, Function<DATASET_FILED[], ICondition> f, IColumnField... columnFields) {
        return this.havingOr(subQuery, true, f, columnFields);
    }

    SELF havingOr(ISubQuery subQuery, boolean when, Function<DATASET_FILED[], ICondition> f, IColumnField... columnFields);
}
