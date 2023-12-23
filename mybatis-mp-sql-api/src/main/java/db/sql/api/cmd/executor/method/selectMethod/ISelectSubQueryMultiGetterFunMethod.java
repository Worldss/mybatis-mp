package db.sql.api.cmd.executor.method.selectMethod;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.IColumnField;
import db.sql.api.cmd.executor.ISubQuery;
import db.sql.api.cmd.executor.method.ISelectMethod;

import java.util.function.Function;

public interface ISelectSubQueryMultiGetterFunMethod<SELF extends ISelectMethod, DATASET_FILED extends Cmd> {

    <T> SELF selectWithFun(ISubQuery subQuery, Function<DATASET_FILED[], Cmd> f, Getter<T>... columns);

    SELF selectWithFun(ISubQuery subQuery, Function<DATASET_FILED[], Cmd> f, IColumnField... columnFields);

    default <T> SELF selectWithFun(boolean when, ISubQuery subQuery, Function<DATASET_FILED[], Cmd> f, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.selectWithFun(subQuery, f, columns);
    }

    default SELF selectWithFun(boolean when, ISubQuery subQuery, Function<DATASET_FILED[], Cmd> f, IColumnField... columnFields) {
        if (!when) {
            return (SELF) this;
        }
        return this.selectWithFun(subQuery, f, columnFields);
    }
}
