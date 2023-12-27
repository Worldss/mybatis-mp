package db.sql.api.cmd.executor.method;

import java.util.function.Consumer;

public interface IFromMethod<SELF extends IFromMethod, TABLE> {

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

    SELF from(Class entity, int storey);



    /**
     * 实体类拦截
     *
     * @param entity
     * @param storey
     */
    default void fromEntityIntercept(Class entity, int storey) {

    }
}
