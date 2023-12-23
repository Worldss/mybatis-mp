package db.sql.api.cmd.executor.method.selectMethod;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.basic.IColumn;
import db.sql.api.cmd.executor.ISubQuery;

import java.util.function.Function;

public interface ISelectMethods<SELF extends ISelectMethods,
        TABLE_FIELD extends Cmd,
        DATASET_FILED extends Cmd,
        COLUMN extends Cmd>
        extends
        ISelectCmdMethod<SELF, COLUMN>,
        ISelectGetterMethod<SELF>,
        ISelectGetterFunMethod<SELF, TABLE_FIELD>,
        ISelectMultiGetterMethod<SELF>,
        ISelectMultiGetterFunMethod<SELF, TABLE_FIELD>,
        ISelectSubQueryMethod<SELF, DATASET_FILED>,
        ISelectSubQueryGetterMethod<SELF, DATASET_FILED>,
        ISelectSubQueryGetterFunMethod<SELF, DATASET_FILED>,
        ISelectSubQueryMultiGetterMethod<SELF>,
        ISelectSubQueryMultiGetterFunMethod<SELF, DATASET_FILED> {

    @Override
    default <T> SELF select(ISubQuery subQuery, Getter<T>... columns) {
        for (Getter<T> column : columns) {
            this.select(subQuery, column);
        }
        return (SELF) this;
    }

    SELF select(String columnName);

    SELF selectWithFun(String columnName, Function<IColumn, Cmd> f);

    default SELF select(boolean when, String columnName) {
        if (!when) {
            return (SELF) this;
        }
        return this.select(columnName);
    }

    default SELF selectWithFun(boolean when, String columnName, Function<IColumn, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.selectWithFun(columnName, f);
    }
}
