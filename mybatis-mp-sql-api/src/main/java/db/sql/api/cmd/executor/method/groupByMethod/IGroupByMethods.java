package db.sql.api.cmd.executor.method.groupByMethod;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.basic.IColumn;
import db.sql.api.cmd.executor.ISubQuery;
import db.sql.api.cmd.executor.method.IGroupByMethod;

import java.util.function.Function;

public interface IGroupByMethods<SELF extends IGroupByMethod,
        TABLE_FIELD extends Cmd,
        DATASET_FILED extends Cmd,
        COLUMN extends Cmd>
        extends
        IGroupByCmdMethod<SELF, COLUMN>,
        IGroupByGetterMethod<SELF>,
        IGroupByGetterFunMethod<SELF, TABLE_FIELD>,
        IGroupByMultiGetterMethod<SELF>,
        IGroupByMultiGetterFunMethod<SELF, TABLE_FIELD>,
        IGroupBySubQueryMethod<SELF, DATASET_FILED>,
        IGroupBySubQueryGetterMethod<SELF, DATASET_FILED>,
        IGroupBySubQueryGetterFunMethod<SELF, DATASET_FILED>,
        IGroupBySubQueryMultiGetterMethod<SELF>,
        IGroupBySubQueryMultiGetterFunMethod<SELF, DATASET_FILED> {

    @Override
    default <T> SELF groupBy(ISubQuery subQuery, Getter<T>... columns) {
        for (Getter<T> column : columns) {
            this.groupBy(subQuery, column);
        }
        return (SELF) this;
    }

    SELF groupBy(String columnName);

    SELF groupByWithFun(String columnName, Function<IColumn, Cmd> f);

    default SELF groupBy(boolean when, String columnName) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupBy(columnName);
    }

    default SELF groupByWithFun(boolean when, String columnName, Function<IColumn, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupByWithFun(columnName, f);
    }
}
