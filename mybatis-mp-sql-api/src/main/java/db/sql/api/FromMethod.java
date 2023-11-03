package db.sql.api;

import java.util.function.Consumer;

public interface FromMethod<SELF extends FromMethod, TABLE> {

    @SuppressWarnings("unchecked")
    SELF from(TABLE... tables);

    @SuppressWarnings("unchecked")
    default SELF from(Class... entities) {
        return this.from(1, entities);
    }

    @SuppressWarnings("unchecked")
    default SELF from(int storey, Class... entities) {
        for (Class entity : entities) {
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
