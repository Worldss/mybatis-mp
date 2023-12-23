package db.sql.api.cmd.executor.method.selectMethod;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.executor.ISubQuery;

import java.util.function.Function;

public interface ISelectSubQueryGetterMethod<SELF extends ISelectSubQueryGetterMethod, DATASET_FILED extends Cmd> {

    <T> SELF select(ISubQuery subQuery, Getter<T> column);

    <T> SELF select(ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, Cmd> f);

    default <T> SELF select(boolean when, ISubQuery subQuery, Getter<T> column) {
        if (!when) {
            return (SELF) this;
        }
        return this.select(subQuery, column);
    }

    default <T> SELF select(boolean when, ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.select(subQuery, column, f);
    }
}
