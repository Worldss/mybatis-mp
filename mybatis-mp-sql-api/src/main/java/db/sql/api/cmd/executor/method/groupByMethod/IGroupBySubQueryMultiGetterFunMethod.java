package db.sql.api.cmd.executor.method.groupByMethod;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.IColumnField;
import db.sql.api.cmd.executor.ISubQuery;

import java.util.function.Function;

public interface IGroupBySubQueryMultiGetterFunMethod<SELF extends IGroupBySubQueryMultiGetterFunMethod, DATASET_FILED extends Cmd> {

    <T> SELF groupByWithFun(ISubQuery subQuery, Function<DATASET_FILED[], Cmd> f, Getter<T>... columns);

    SELF groupByWithFun(ISubQuery subQuery, Function<DATASET_FILED[], Cmd> f, IColumnField... columnFields);

    default <T> SELF groupByWithFun(boolean when, ISubQuery subQuery, Function<DATASET_FILED[], Cmd> f, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupByWithFun(subQuery, f, columns);
    }

    default SELF groupByWithFun(boolean when, ISubQuery subQuery, Function<DATASET_FILED[], Cmd> f, IColumnField... columnFields) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupByWithFun(subQuery, f, columnFields);
    }
}
