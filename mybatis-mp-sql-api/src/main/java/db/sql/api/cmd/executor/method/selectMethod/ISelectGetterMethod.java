package db.sql.api.cmd.executor.method.selectMethod;


import db.sql.api.Getter;
import db.sql.api.cmd.executor.method.ISelectMethod;

public interface ISelectGetterMethod<SELF extends ISelectMethod>  {

    default <T> SELF select(Getter<T> column) {
        return this.select( column, 1);
    }

    <T> SELF select( Getter<T> column, int storey);

    default <T> SELF select(boolean when, Getter<T> column) {
        if (!when) {
            return (SELF) this;
        }
        return this.select( column, 1);
    }

    default <T> SELF select(boolean when, Getter<T> column, int storey) {
        if (!when) {
            return (SELF) this;
        }
        return this.select( column, storey);
    }
}
