package db.sql.api;

import java.util.function.Consumer;

public interface FromMethod<SELF extends FromMethod, TABLE> {

    SELF from(TABLE... tables);

    default SELF from(Class... tables) {
        return this.from(1, tables);
    }

    default SELF from(int storey, Class... entitys) {
        for (Class entity : entitys) {
            this.from(entity, storey);
        }
        return (SELF) this;
    }

    default SELF from(Class entity, int storey) {
        return this.from(entity, storey, null);
    }

    default SELF from(Class entity, Consumer<TABLE> consumer) {
        return this.from(entity, 1, consumer);
    }

    SELF from(Class entity, int storey, Consumer<TABLE> consumer);
}
