package db.sql.api.cmd.executor.method;

import db.sql.api.cmd.executor.method.havingMethod.IHavingMethods;
import db.sql.api.cmd.struct.query.IHaving;

import java.util.function.Consumer;

public interface IHavingMethod<SELF extends IHavingMethod,
        TABLE_FIELD,
        DATASET_FILED,
        HAVING extends IHaving<HAVING>
        >
        extends IHavingMethods<SELF, TABLE_FIELD, DATASET_FILED> {

    SELF having(Consumer<HAVING> consumer);

}
