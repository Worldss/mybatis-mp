package db.sql.api.cmd.executor.method.selectMethod;


import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.executor.method.ISelectMethod;

import java.util.function.Function;

public interface ISelectGetterFunMethod<SELF extends ISelectMethod, TABLE_FIELD extends Cmd>  {

    default <T> SELF selectWithFun(Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        return this.selectWithFun( column, 1, f);
    }

    <T> SELF selectWithFun(Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f);

    default <T> SELF selectWithFun(boolean when, Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.selectWithFun( column, 1, f);
    }

    default <T> SELF selectWithFun(boolean when, Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.selectWithFun( column, storey, f);
    }

    default <T> SELF select(boolean when, Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.selectWithFun( column, 1, f);
    }


    default <T> SELF select(boolean when, Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.selectWithFun( column, storey, f);
    }
}
