package db.sql.api.cmd.executor.method;

import db.sql.api.Getter;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.executor.ISubQuery;
import db.sql.api.cmd.struct.query.IHaving;

import java.util.function.Consumer;
import java.util.function.Function;

public interface HavingMethod<SELF extends HavingMethod, TABLE_FIELD, DATASET_FILED, HAVING extends IHaving<HAVING>> {


    SELF having(Consumer<HAVING> consumer);

    default SELF having(ICondition condition) {
        return this.havingAnd(condition);
    }

    SELF havingAnd(ICondition condition);

    SELF havingOr(ICondition condition);

    default <T> SELF havingAnd(Getter<T> column, Function<TABLE_FIELD, ICondition> f) {
        return this.havingAnd(column, 1, f);
    }

    <T> SELF havingAnd(Getter<T> column, int storey, Function<TABLE_FIELD, ICondition> f);

    default <T> SELF havingOr(Getter<T> column, Function<TABLE_FIELD, ICondition> f) {
        return this.havingOr(column, 1, f);
    }

    <T> SELF havingOr(Getter<T> column, int storey, Function<TABLE_FIELD, ICondition> f);


    default <T> SELF having(Getter<T> column, Function<TABLE_FIELD, ICondition> f) {
        return this.having(column, 1, f);
    }

    default <T> SELF having(Getter<T> column, int storey, Function<TABLE_FIELD, ICondition> f) {
        return this.havingAnd(column, storey, f);
    }

    SELF havingAnd(ISubQuery subQuery, String columnName, Function<DATASET_FILED, ICondition> f);

    <T> SELF havingAnd(ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, ICondition> f);

    SELF havingOr(ISubQuery subQuery, String columnName, Function<DATASET_FILED, ICondition> f);

    <T> SELF havingOr(ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, ICondition> f);

    default <T> SELF having(ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, ICondition> f) {
        return this.havingAnd(subQuery, column, f);
    }

    default <T> SELF having(ISubQuery subQuery, String columnName, Function<DATASET_FILED, ICondition> f) {
        return this.havingAnd(subQuery, columnName, f);
    }
}
