package db.sql.api.cmd.executor.method.groupByMethod;


import db.sql.api.Getter;
import db.sql.api.cmd.executor.ISubQuery;
import db.sql.api.cmd.executor.method.IGroupByMethod;

public interface IGroupBySubQueryMultiGetterMethod<SELF extends IGroupByMethod> {

    <T> SELF groupBy(ISubQuery subQuery, Getter<T>... columns);

    default <T> SELF groupBy(boolean when, ISubQuery subQuery, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupBy(subQuery, columns);
    }
}
