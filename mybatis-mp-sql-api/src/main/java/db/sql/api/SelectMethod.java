package db.sql.api;

import java.util.List;
import java.util.function.Function;

public interface SelectMethod<SELF extends SelectMethod, TABLE_FIELD, COLUMN> {

    SELF select(COLUMN column);

    default SELF select(COLUMN... columns) {
        for (COLUMN column : columns) {
            this.select(column);
        }
        return (SELF) this;
    }

    default SELF select(List<COLUMN> columns) {
        for (COLUMN column : columns) {
            this.select(column);
        }
        return (SELF) this;
    }

    default <T> SELF select(Getter<T> column) {
        return this.select(column, 1);
    }

    default <T> SELF select(Getter<T> column, Function<TABLE_FIELD, Cmd> f) {
        return this.select(column, 1, f);
    }

    default <T> SELF select(Getter<T> column, int storey) {
        return this.select(column, storey, null);
    }

    <T> SELF select(Getter<T> column, int storey, Function<TABLE_FIELD, Cmd> f);

    default <T> SELF select(Getter<T>... columns) {
        return this.select(1, columns);
    }

    default <T> SELF select(int storey, Getter<T>... columns) {
        for (Getter<T> column : columns) {
            this.select(column, storey);
        }
        return (SELF) this;
    }

    default SELF select(Class entity) {
        return this.select(entity, 1);
    }

    SELF select(Class entity, int storey);

    default SELF select(Class... entitys) {
        return this.select(1, entitys);
    }

    default SELF select(int storey, Class... entitys) {
        for (Class entity : entitys) {
            this.select(entity, storey);
        }
        return (SELF) this;
    }
}
