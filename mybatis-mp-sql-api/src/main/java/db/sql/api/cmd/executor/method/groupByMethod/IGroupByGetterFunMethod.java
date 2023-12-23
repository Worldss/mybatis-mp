package db.sql.api.cmd.executor.method.groupByMethod;


import db.sql.api.Cmd;
import db.sql.api.Getter;

import java.util.function.Function;

public interface IGroupByGetterFunMethod<SELF extends IGroupByGetterFunMethod, TABLE_FIELD extends Cmd> {

    default <T> SELF groupByWithFun(Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        return this.groupByWithFun(column, 1, f);
    }

    <T> SELF groupByWithFun(Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f);

    default <T> SELF groupByWithFun(boolean when, Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupByWithFun(column, 1, f);
    }

    default <T> SELF groupByWithFun(boolean when, Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupByWithFun(column, storey, f);
    }

    default <T> SELF groupBy(boolean when, Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupByWithFun(column, 1, f);
    }


    default <T> SELF groupBy(boolean when, Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f) {
        if (!when) {
            return (SELF) this;
        }
        return this.groupByWithFun(column, storey, f);
    }
}
