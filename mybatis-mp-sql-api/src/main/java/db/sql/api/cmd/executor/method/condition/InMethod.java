package db.sql.api.cmd.executor.method.condition;

import db.sql.api.Getter;
import db.sql.api.cmd.executor.IQuery;

import java.io.Serializable;
import java.util.List;

public interface InMethod<RV, COLUMN> {

    default RV in(COLUMN column, IQuery query) {
        return this.in(column, true, query);
    }

    RV in(COLUMN column, boolean when, IQuery query);

    default RV in(COLUMN column, Serializable... values) {
        return this.in(column, true, values);
    }

    RV in(COLUMN column, boolean when, Serializable... values);

    default RV in(COLUMN column, List<Serializable> values) {
        return this.in(column, true, values);
    }

    RV in(COLUMN column, boolean when, List<Serializable> values);

    default <T> RV in(Getter<T> column, IQuery query) {
        return this.in(column, true, query);
    }

    default <T> RV in(Getter<T> column, boolean when, IQuery query) {
        return this.in(column, 1, when, query);
    }

    default <T> RV in(Getter<T> column, int storey, IQuery query) {
        return this.in(column, storey, true, query);
    }

    <T> RV in(Getter<T> column, int storey, boolean when, IQuery query);

    default <T> RV in(Getter<T> column, Serializable[] values) {
        return this.in(column, true, values);
    }

    default <T> RV in(Getter<T> column, boolean when, Serializable... values) {
        return this.in(column, 1, when, values);
    }

    default <T> RV in(Getter<T> column, int storey, Serializable[] values) {
        return this.in(column, storey, true, values);
    }

    <T> RV in(Getter<T> column, int storey, boolean when, Serializable... values);

    default <T> RV in(Getter<T> column, List<Serializable> values) {
        return this.in(column, true, values);
    }

    default <T> RV in(Getter<T> column, boolean when, List<Serializable> values) {
        return this.in(column, 1, when, values);
    }

    default <T> RV in(Getter<T> column, int storey, List<Serializable> values) {
        return this.in(column, storey, true, values);
    }

    <T> RV in(Getter<T> column, int storey, boolean when, List<Serializable> values);
}
