package db.sql.api.cmd.executor.method.selectMethod;


import db.sql.api.Getter;
import db.sql.api.cmd.executor.method.ISelectMethod;

public interface ISelectMultiGetterMethod<SELF extends ISelectMethod> {

    default <T> SELF select(Getter<T>... columns) {
        return this.select(1, columns);
    }

    <T> SELF select(int storey, Getter<T>... columns);

    default <T> SELF select(boolean when, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.select(1, columns);
    }

    default <T> SELF select(boolean when, int storey, Getter<T>... columns) {
        if (!when) {
            return (SELF) this;
        }
        return this.select(storey, columns);
    }
}
