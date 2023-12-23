package db.sql.api.cmd.executor.method.groupByMethod;


import db.sql.api.Getter;

public interface IGroupByMultiGetterMethod<SELF extends IGroupByMultiGetterMethod> {

    default <T> SELF groupBy(Getter<T>... columns) {
        return this.groupBy(1, columns);
    }

    <T> SELF groupBy(int storey, Getter<T>... columns);

    default <T> SELF groupBy(boolean when, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupBy(1, columns);
    }

    default <T> SELF groupBy(boolean when, int storey, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupBy(storey, columns);
    }
}
