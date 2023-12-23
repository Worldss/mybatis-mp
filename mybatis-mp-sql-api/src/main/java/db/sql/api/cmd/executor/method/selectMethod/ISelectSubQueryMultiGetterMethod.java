package db.sql.api.cmd.executor.method.selectMethod;


import db.sql.api.Getter;
import db.sql.api.cmd.executor.ISubQuery;
import db.sql.api.cmd.executor.method.ISelectMethod;

public interface ISelectSubQueryMultiGetterMethod<SELF extends ISelectMethod> {

    <T> SELF select(ISubQuery subQuery, Getter<T>... columns);

    default <T> SELF select(boolean when, ISubQuery subQuery, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.select(subQuery, columns);
    }
}
