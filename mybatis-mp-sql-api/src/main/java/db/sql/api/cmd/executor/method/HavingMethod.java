package db.sql.api.cmd.executor.method;

import db.sql.api.Getter;
import db.sql.api.cmd.basic.Condition;
import db.sql.api.cmd.executor.SubQuery;
import db.sql.api.cmd.struct.query.Having;

import java.util.function.Consumer;
import java.util.function.Function;

public interface HavingMethod<SELF extends HavingMethod, TABLE_FIELD, SUB_QUERY_TABLE_FIELD, HAVING extends Having<HAVING>> {


    SELF having(Consumer<HAVING> consumer);

    default SELF having(Condition condition) {
        return this.havingAnd(condition);
    }

    SELF havingAnd(Condition condition);

    SELF havingOr(Condition condition);

    default <T> SELF havingAnd(Getter<T> column, Function<TABLE_FIELD, Condition> f) {
        return this.havingAnd(column, 1, f);
    }

    <T> SELF havingAnd(Getter<T> column, int storey, Function<TABLE_FIELD, Condition> f);

    default <T> SELF havingOr(Getter<T> column, Function<TABLE_FIELD, Condition> f) {
        return this.havingOr(column, 1, f);
    }

    <T> SELF havingOr(Getter<T> column, int storey, Function<TABLE_FIELD, Condition> f);


    default <T> SELF having(Getter<T> column, Function<TABLE_FIELD, Condition> f) {
        return this.having(column, 1, f);
    }

    default <T> SELF having(Getter<T> column, int storey, Function<TABLE_FIELD, Condition> f) {
        return this.havingAnd(column, storey, f);
    }


    default <T> SELF havingAnd(SubQuery subQuery, Getter<T> column, Function<SUB_QUERY_TABLE_FIELD, Condition> f) {
        return this.havingAnd(subQuery, column, 1, f);
    }

    <T> SELF havingAnd(SubQuery subQuery, Getter<T> column, int storey, Function<SUB_QUERY_TABLE_FIELD, Condition> f);

    default <T> SELF havingOr(SubQuery subQuery, Getter<T> column, Function<SUB_QUERY_TABLE_FIELD, Condition> f) {
        return this.havingOr(subQuery, column, 1, f);
    }

    <T> SELF havingOr(SubQuery subQuery, Getter<T> column, int storey, Function<SUB_QUERY_TABLE_FIELD, Condition> f);

    default <T> SELF having(SubQuery subQuery, Getter<T> column, Function<SUB_QUERY_TABLE_FIELD, Condition> f) {
        return this.having(subQuery, column, 1, f);
    }

    default <T> SELF having(SubQuery subQuery, Getter<T> column, int storey, Function<SUB_QUERY_TABLE_FIELD, Condition> f) {
        return this.havingAnd(subQuery, column, storey, f);
    }
}
