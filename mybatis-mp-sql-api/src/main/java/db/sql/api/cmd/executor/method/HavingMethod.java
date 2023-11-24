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

    default <T> SELF havingAnd(Getter<T> getter, Function<TABLE_FIELD, Condition> f) {
        return this.havingAnd(getter, 1, f);
    }

    <T> SELF havingAnd(Getter<T> getter, int storey, Function<TABLE_FIELD, Condition> f);

    default <T> SELF havingOr(Getter<T> getter, Function<TABLE_FIELD, Condition> f) {
        return this.havingOr(getter, 1, f);
    }

    <T> SELF havingOr(Getter<T> getter, int storey, Function<TABLE_FIELD, Condition> f);


    default <T> SELF having(Getter<T> getter, Function<TABLE_FIELD, Condition> f) {
        return this.having(getter, 1, f);
    }

    default <T> SELF having(Getter<T> getter, int storey, Function<TABLE_FIELD, Condition> f) {
        return this.havingAnd(getter, storey, f);
    }


    default <T> SELF havingAnd(SubQuery subQuery, Getter<T> getter, Function<SUB_QUERY_TABLE_FIELD, Condition> f) {
        return this.havingAnd(subQuery, getter, 1, f);
    }

    <T> SELF havingAnd(SubQuery subQuery, Getter<T> getter, int storey, Function<SUB_QUERY_TABLE_FIELD, Condition> f);

    default <T> SELF havingOr(SubQuery subQuery, Getter<T> getter, Function<SUB_QUERY_TABLE_FIELD, Condition> f) {
        return this.havingOr(subQuery, getter, 1, f);
    }

    <T> SELF havingOr(SubQuery subQuery, Getter<T> getter, int storey, Function<SUB_QUERY_TABLE_FIELD, Condition> f);

    default <T> SELF having(SubQuery subQuery, Getter<T> getter, Function<SUB_QUERY_TABLE_FIELD, Condition> f) {
        return this.having(subQuery, getter, 1, f);
    }

    default <T> SELF having(SubQuery subQuery, Getter<T> getter, int storey, Function<SUB_QUERY_TABLE_FIELD, Condition> f) {
        return this.havingAnd(subQuery, getter, storey, f);
    }
}
