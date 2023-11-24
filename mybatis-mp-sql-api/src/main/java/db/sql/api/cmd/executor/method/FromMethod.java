package db.sql.api.cmd.executor.method;

import java.util.function.Consumer;

public interface FromMethod<SELF extends FromMethod, TABLE> {

    SELF from(TABLE... tables);

    default SELF from(Class... entities) {
        return this.from(1, entities);
    }

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

    /**
     * 实体类拦截
     *
     * @param entity
     * @param storey
     */
    default void fromEntityIntercept(Class entity, int storey) {

    }
}
