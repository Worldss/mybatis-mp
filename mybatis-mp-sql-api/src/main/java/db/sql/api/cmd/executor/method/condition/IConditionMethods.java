package db.sql.api.cmd.executor.method.condition;

/**
 * 条件方法
 *
 * @param <RV>
 * @param <COLUMN>
 * @param <V>
 */
public interface IConditionMethods<RV, COLUMN, V> extends ICompare<RV, COLUMN, V>,
        IInMethod<RV, COLUMN>,
        IExistsMethod<RV> {
}
