package db.sql.api.cmd.executor.method.selectMethod;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.executor.ISubQuery;
import db.sql.api.cmd.executor.method.ISelectMethod;

import java.util.function.Function;

public interface ISelectSubQueryGetterFunMethod<SELF extends ISelectMethod, DATASET_FILED extends Cmd> {

    <T> SELF selectWithFun(ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, Cmd> f);

    default <T> SELF selectWithFun(boolean when, ISubQuery subQuery, Getter<T> column, Function<DATASET_FILED, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.selectWithFun(subQuery, column, f);
    }
}
