package db.sql.api.cmd.executor.method.groupByMethod;


import db.sql.api.Cmd;
import db.sql.api.cmd.executor.ISubQuery;
import db.sql.api.cmd.executor.method.IGroupByMethod;

import java.util.function.Function;

public interface IGroupBySubQueryMethod<SELF extends IGroupByMethod, DATASET_FILED extends Cmd> {

    SELF groupBy(ISubQuery subQuery, String columnName);

    SELF groupByWithFun(ISubQuery subQuery, String columnName, Function<DATASET_FILED, Cmd> f);

    default SELF groupBy(boolean when, ISubQuery subQuery, String columnName) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupBy(subQuery, columnName);
    }

    default SELF groupByWithFun(boolean when, ISubQuery subQuery, String columnName, Function<DATASET_FILED, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupByWithFun(subQuery, columnName, f);
    }
}
