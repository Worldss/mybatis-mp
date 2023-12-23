package db.sql.api.cmd.executor.method;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.basic.Count1;
import db.sql.api.cmd.basic.CountAll;
import db.sql.api.cmd.basic.SQL1;
import db.sql.api.cmd.basic.SQLCmdAll;
import db.sql.api.cmd.executor.method.selectMethod.ISelectMethods;

public interface ISelectMethod<SELF extends ISelectMethod,
        TABLE_FIELD extends DATASET_FILED,
        DATASET_FILED extends Cmd,
        COLUMN extends Cmd>

        extends ISelectMethods<SELF,
        TABLE_FIELD,
        DATASET_FILED,
        COLUMN> {

    SELF selectDistinct();

    default SELF select1() {
        this.select((COLUMN) SQL1.INSTANCE);
        return (SELF) this;
    }

    default SELF selectAll() {
        this.select((COLUMN) SQLCmdAll.INSTANCE);
        return (SELF) this;
    }

    default SELF selectCount1() {
        this.select((COLUMN) Count1.INSTANCE);
        return (SELF) this;
    }

    default SELF selectCountAll() {
        this.select((COLUMN) CountAll.INSTANCE);
        return (SELF) this;
    }

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

    default <T> SELF selectIgnore(Getter<T> column) {
        return this.selectIgnore(column, 1);
    }

    <T> SELF selectIgnore(Getter<T> column, int storey);

    default <T> SELF selectIgnore(Getter<T>... columns) {
        return this.selectIgnore(1, columns);
    }

    default <T> SELF selectIgnore(int storey, Getter<T>... columns) {
        for (Getter column : columns) {
            this.selectIgnore(column, storey);
        }
        return (SELF) this;
    }
}
