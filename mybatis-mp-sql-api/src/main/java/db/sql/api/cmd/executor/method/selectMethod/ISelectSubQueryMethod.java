package db.sql.api.cmd.executor.method.selectMethod;


import db.sql.api.Cmd;
import db.sql.api.cmd.executor.ISubQuery;

import java.util.function.Function;

public interface ISelectSubQueryMethod<SELF extends ISelectSubQueryMethod, DATASET_FILED extends Cmd> {

    SELF select(ISubQuery subQuery, String columnName);

    SELF selectWithFun(ISubQuery subQuery, String columnName, Function<DATASET_FILED, Cmd> f);

    default SELF select(boolean when, ISubQuery subQuery, String columnName) {
        if (!when) {
            return (SELF) this;
        }
        return this.select(subQuery, columnName);
    }

    default SELF selectWithFun(boolean when, ISubQuery subQuery, String columnName, Function<DATASET_FILED, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.selectWithFun(subQuery, columnName, f);
    }
}
