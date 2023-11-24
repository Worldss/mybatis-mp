package db.sql.api.cmd.executor.method;

import db.sql.api.cmd.executor.method.condition.ExistsMethod;
import db.sql.api.cmd.executor.method.condition.InMethod;
import db.sql.api.cmd.executor.method.condition.compare.Compare;

/**
 * 条件方法
 *
 * @param <RV>
 * @param <COLUMN>
 * @param <V>
 */
public interface ConditionMethods<RV, COLUMN, V> extends Compare<RV, COLUMN, V>,
        InMethod<RV, COLUMN>,
        ExistsMethod<RV> {
}
