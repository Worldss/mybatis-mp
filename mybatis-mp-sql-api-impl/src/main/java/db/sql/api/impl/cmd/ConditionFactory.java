package db.sql.api.impl.cmd;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.LikeMode;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.executor.IQuery;
import db.sql.api.cmd.executor.method.condition.ConditionMethods;

import java.io.Serializable;
import java.util.List;

public class ConditionFactory implements ConditionMethods<ICondition, Cmd, Object> {

    protected final CmdFactory cmdFactory;

    public ConditionFactory(CmdFactory cmdFactory) {
        this.cmdFactory = cmdFactory;
    }

    public CmdFactory getCmdFactory() {
        return cmdFactory;
    }

    protected boolean ignoreEmpty() {
        return false;
    }

    protected boolean hasValue(Object value) {
        if (value == null) {
            return false;
        } else if (ignoreEmpty() && value instanceof String) {
            String v = (String) value;
            return !v.trim().isEmpty();
        }
        return true;
    }


    private boolean isValid(boolean allNeedValue, Object... params) {
        if (allNeedValue) {
            for (Object param : params) {
                if (!(hasValue(param))) {
                    return false;
                }
            }
            return true;
        } else {
            for (Object param : params) {
                if (hasValue(param)) {
                    return true;
                }
            }
            return false;
        }
    }

    private boolean isValid(boolean allNeedValue, List<Object> params) {
        if (allNeedValue) {
            for (Object param : params) {
                if (!(hasValue(param))) {
                    return false;
                }
            }
            return true;
        } else {
            for (Object param : params) {
                if (hasValue(param)) {
                    return true;
                }
            }
            return false;
        }
    }

    protected boolean isValid(Cmd filed) {
        return filed != null;
    }

    protected boolean isValid(Object value) {
        return value != null;
    }

    protected boolean isValid(Cmd filed, Object param) {
        return filed != null && hasValue(param);
    }

    protected boolean isValid(Cmd filed, boolean allNeedValue, Object... params) {
        if (filed == null || params == null || params.length < 1) {
            return false;
        }
        return this.isValid(allNeedValue, params);
    }

    protected boolean isValid(Cmd filed, boolean allNeedValue, List<Object> params) {
        if (filed == null || params == null || params.isEmpty()) {
            return false;
        }
        return this.isValid(allNeedValue, params);
    }

    private Cmd convert(Object value) {
        Cmd v1;
        if (value instanceof Cmd) {
            v1 = (Cmd) value;
        } else {
            v1 = cmdFactory.value(value);
        }
        return v1;
    }

    private <T> Cmd convert(Getter<T> column, int storey) {
        return cmdFactory.field(column, storey);
    }

    @Override
    public ICondition empty(Cmd column, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(column)) {
            return null;
        }
        return Methods.eq(column, cmdFactory.value(""));
    }

    @Override
    public <T> ICondition empty(Getter<T> column, int storey, boolean when) {
        if (!when) {
            return null;
        }
        return Methods.eq(convert(column, storey), cmdFactory.value(""));
    }

    @Override
    public ICondition notEmpty(Cmd column, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(column)) {
            return null;
        }
        return Methods.ne(column, cmdFactory.value(""));
    }

    @Override
    public <T> ICondition notEmpty(Getter<T> column, int storey, boolean when) {
        if (!when) {
            return null;
        }
        return Methods.ne(convert(column, storey), cmdFactory.value(""));
    }

    @Override
    public ICondition eq(Cmd column, Object value, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(column, value)) {
            return null;
        }
        return Methods.eq(column, convert(value));
    }

    @Override
    public ICondition ne(Cmd column, Object value, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(column, value)) {
            return null;
        }
        return Methods.ne(column, convert(value));
    }

    @Override
    public ICondition gt(Cmd column, Object value, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(column, value)) {
            return null;
        }
        return Methods.gt(column, convert(value));
    }

    @Override
    public ICondition gte(Cmd column, Object value, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(column, value)) {
            return null;
        }
        return Methods.gte(column, convert(value));
    }

    @Override
    public ICondition lt(Cmd column, Object value, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(column, value)) {
            return null;
        }
        return Methods.lt(column, convert(value));
    }

    @Override
    public ICondition lte(Cmd column, Object value, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(column, value)) {
            return null;
        }
        return Methods.lte(column, convert(value));
    }

    @Override
    public ICondition like(Cmd column, String value, LikeMode mode, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(column, value)) {
            return null;
        }
        return Methods.like(column, value, mode);
    }

    @Override
    public ICondition notLike(Cmd column, String value, LikeMode mode, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(column, value)) {
            return null;
        }
        return Methods.notLike(column, value, mode);
    }

    @Override
    public ICondition between(Cmd column, Serializable value, Serializable value2, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(column, false, value, value2)) {
            return null;
        }
        return Methods.between(column, value, value2);
    }

    @Override
    public <T> ICondition between(Getter<T> column, Serializable value, Serializable value2, int storey, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(false, value, value2)) {
            return null;
        }
        return Methods.between(convert(column, storey), value, value2);
    }

    @Override
    public ICondition notBetween(Cmd column, Serializable value, Serializable value2, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(column, false, value, value2)) {
            return null;
        }
        return Methods.notBetween(column, value, value2);
    }

    @Override
    public <T> ICondition notBetween(Getter<T> column, Serializable value, Serializable value2, int storey, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(false, value, value2)) {
            return null;
        }
        return Methods.notBetween(convert(column, storey), value, value2);
    }

    @Override
    public ICondition isNull(Cmd column, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(column)) {
            return null;
        }
        return Methods.isNull(column);
    }

    @Override
    public ICondition isNotNull(Cmd column, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(column)) {
            return null;
        }
        return Methods.isNotNull(column);
    }

    @Override
    public <T> ICondition eq(Getter<T> column, Object value, int storey, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(value)) {
            return null;
        }
        return Methods.eq(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> ICondition eq(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!when) {
            return null;
        }
        return Methods.eq(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> ICondition gt(Getter<T> column, Object value, int storey, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(value)) {
            return null;
        }
        return Methods.gt(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> ICondition gt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!when) {
            return null;
        }
        return Methods.gt(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> ICondition gte(Getter<T> column, Object value, int storey, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(value)) {
            return null;
        }
        return Methods.gte(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> ICondition gte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!when) {
            return null;
        }
        return Methods.gte(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> ICondition like(Getter<T> column, String value, LikeMode mode, int storey, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(value)) {
            return null;
        }
        return Methods.like(convert(column, storey), value, mode);
    }

    @Override
    public <T> ICondition lt(Getter<T> column, Object value, int storey, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(value)) {
            return null;
        }
        return Methods.lt(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> ICondition lt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!when) {
            return null;
        }
        return Methods.lt(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> ICondition lte(Getter<T> column, Object value, int storey, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(value)) {
            return null;
        }
        return Methods.lte(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> ICondition lte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!when) {
            return null;
        }
        return Methods.lte(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> ICondition ne(Getter<T> column, Object value, int storey, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(value)) {
            return null;
        }
        return Methods.ne(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> ICondition ne(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!when) {
            return null;
        }
        return Methods.ne(convert(column, columnStorey), convert(value, valueStorey));
    }


    @Override
    public <T> ICondition notLike(Getter<T> column, String value, LikeMode mode, int storey, boolean when) {
        if (!when) {
            return null;
        }
        if (!isValid(value)) {
            return null;
        }
        return Methods.notLike(convert(column, storey), value, mode);
    }

    @Override
    public <T> ICondition isNull(Getter<T> column, int storey, boolean when) {
        if (!when) {
            return null;
        }
        return Methods.isNull(convert(column, storey));
    }

    @Override
    public <T> ICondition isNotNull(Getter<T> column, int storey, boolean when) {
        if (!when) {
            return null;
        }
        return Methods.isNotNull(convert(column, storey));
    }

    @Override
    public ICondition in(Cmd column, boolean when, IQuery query) {
        if (!when) {
            return null;
        }
        if (!isValid(column, query)) {
            return null;
        }
        return Methods.in(column, query);
    }

    @Override
    public ICondition in(Cmd column, boolean when, Serializable... values) {
        if (!when) {
            return null;
        }
        if (values != null && values.length > 0 && values[0] instanceof Serializable[]) {
            values = (Serializable[]) values[0];
        }
        if (!isValid(column, false, values)) {
            return null;
        }
        return Methods.in(column, values);
    }

    @Override
    public ICondition in(Cmd column, boolean when, List<Serializable> values) {
        if (!when) {
            return null;
        }
        if (!isValid(column, false, values)) {
            return null;
        }
        return Methods.in(column, values);
    }

    @Override
    public <T> ICondition in(Getter<T> column, int storey, boolean when, IQuery query) {
        if (!when) {
            return null;
        }
        if (!isValid(query)) {
            return null;
        }
        return Methods.in(convert(column, storey), query);
    }

    @Override
    public <T> ICondition in(Getter<T> column, int storey, boolean when, Serializable... values) {
        if (!when) {
            return null;
        }
        if (!isValid(false, values)) {
            return null;
        }
        return Methods.in(convert(column, storey), values);
    }

    @Override
    public <T> ICondition in(Getter<T> column, int storey, boolean when, List<Serializable> values) {
        if (!when) {
            return null;
        }
        if (!isValid(false, values)) {
            return null;
        }
        return Methods.in(convert(column, storey), values);
    }

    @Override
    public ICondition exists(boolean when, IQuery query) {
        if (!when) {
            return null;
        }
        if (!isValid(query)) {
            return null;
        }
        return Methods.exists(query);
    }


    @Override
    public ICondition notExists(boolean when, IQuery query) {
        if (!when) {
            return null;
        }
        if (!isValid(query)) {
            return null;
        }
        return Methods.notExists(query);
    }
}
