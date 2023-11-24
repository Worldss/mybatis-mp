package db.sql.api.cmd.executor.method;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.basic.Count1;
import db.sql.api.cmd.basic.CountAll;
import db.sql.api.cmd.basic.SQL1;
import db.sql.api.cmd.basic.SQLCmdAll;

import java.util.List;
import java.util.function.Function;

public interface SelectMethod<SELF extends SelectMethod, TABLE_FIELD> {

    SELF select(Cmd column);

    SELF selectDistinct();

    default SELF select1() {
        this.select(SQL1.INSTANCE);
        return (SELF) this;
    }

    default SELF selectAll() {
        this.select(SQLCmdAll.INSTANCE);
        return (SELF) this;
    }

    default SELF selectCount1() {
        this.select(Count1.INSTANCE);
        return (SELF) this;
    }

    default SELF selectCountAll() {
        this.select(CountAll.INSTANCE);
        return (SELF) this;
    }

    default SELF select(Cmd... columns) {
        for (Cmd column : columns) {
            this.select(column);
        }
        return (SELF) this;
    }

    default SELF select(List<Cmd> columns) {
        for (Cmd column : columns) {
            this.select(column);
        }
        return (SELF) this;
    }

    default <T> SELF select(Getter<T> column) {
        return this.select(column, 1);
    }

    default <T> SELF select(Getter<T>... columns) {
        return this.select(1, columns);
    }

    default <T> SELF select(int storey, Getter<T>... columns) {
        for (Getter<T> column : columns) {
            this.select(column, storey);
        }
        return (SELF) this;
    }

    default <T> SELF select(Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        return this.select(column, 1, f);
    }

    default <T> SELF select(Getter<T> column, int storey) {
        return this.select(column, storey, null);
    }

    <T> SELF select(Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f);

    default SELF select(Class entity) {
        return this.select(entity, 1);
    }

    SELF select(Class entity, int storey);

    default SELF select(Class... entities) {
        return this.select(1, entities);
    }

    default SELF select(int storey, Class... entities) {
        for (Class entity : entities) {
            this.select(entity, storey);
        }
        return (SELF) this;
    }



}
