package db.sql.api.cmd.executor.method.groupByMethod;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.executor.ISubQuery;
import db.sql.api.cmd.executor.method.IGroupByMethod;

import java.util.function.Function;

public interface IGroupBySubQueryGetterMethod<SELF extends IGroupByMethod, DATASET_FILED extends Cmd> {

    <T> SELF groupBy(ISubQuery subQuery, Getter<T> column);

    <T> SELF groupBy(ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, Cmd> f);

    default <T> SELF groupBy(boolean when, ISubQuery subQuery, Getter<T> column) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupBy(subQuery, column);
    }

    default <T> SELF groupBy(boolean when, ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupBy(subQuery, column, f);
    }
}
