package db.sql.api.cmd.executor.method.condition;

import db.sql.api.cmd.executor.method.condition.compare.Compare;

/**
 * 条件方法
 *
 * @param <RV>
 * @param <COLUMN>
 * @param <V>
 */
public interface ConditionMethods<RV, COLUMN, V> extends Compare<RV, COLUMN, V>,
        InConditionMethod<RV, COLUMN> {
}
