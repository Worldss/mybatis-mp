package db.sql.api.cmd.executor.method;

import db.sql.api.Cmd;
import db.sql.api.Getter;

import java.util.List;
import java.util.function.Function;

public interface GroupByMethod<SELF extends GroupByMethod, TABLE_FIELD, COLUMN> {

    SELF groupBy(COLUMN column);


    default SELF groupBy(COLUMN... columns) {
        for (COLUMN column : columns) {
            this.groupBy(column);
        }
        return (SELF) this;
    }


    default SELF groupBy(List<COLUMN> columns) {
        for (COLUMN column : columns) {
            this.groupBy(column);
        }
        return (SELF) this;
    }

    default <T> SELF groupBy(Getter<T> column) {
        return this.groupBy(column, 1);
    }

    default <T> SELF groupBy(Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        return this.groupBy(column, 1, f);
    }

    default <T> SELF groupBy(Getter<T> column, int storey) {
        return this.groupBy(column, storey, null);
    }

    <T> SELF groupBy(Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f);


    default <T> SELF groupBy(Getter<T>... columns) {
        return this.groupBy(1, columns);
    }


    default <T> SELF groupBy(int storey, Getter<T>... columns) {
        for (Getter<T> column : columns) {
            this.groupBy(column, storey);
        }
        return (SELF) this;
    }
}
