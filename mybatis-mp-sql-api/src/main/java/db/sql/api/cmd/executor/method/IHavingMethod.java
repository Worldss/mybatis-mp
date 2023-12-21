package db.sql.api.cmd.executor.method;

import db.sql.api.Getter;
import db.sql.api.cmd.GetterField;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.executor.ISubQuery;
import db.sql.api.cmd.executor.method.havingMethod.IHavingAndMethod;
import db.sql.api.cmd.executor.method.havingMethod.IHavingMethods;
import db.sql.api.cmd.struct.query.IHaving;

import java.util.function.Consumer;
import java.util.function.Function;

public interface IHavingMethod<SELF extends IHavingMethod,
        TABLE_FIELD,
        DATASET_FILED,
        HAVING extends IHaving<HAVING>
        >
        extends IHavingMethods<SELF, TABLE_FIELD, DATASET_FILED> {

    SELF having(Consumer<HAVING> consumer);

}
