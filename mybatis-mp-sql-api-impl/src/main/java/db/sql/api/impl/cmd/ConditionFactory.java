package db.sql.api.impl.cmd;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.LikeMode;
import db.sql.api.cmd.basic.Condition;
import db.sql.api.cmd.executor.Query;
import db.sql.api.cmd.executor.method.ConditionMethods;


import java.io.Serializable;
import java.util.List;

public class ConditionFactory implements ConditionMethods<Condition, Cmd, Object> {

    protected final CmdFactory cmdFactory;

    public ConditionFactory(CmdFactory cmdFactory) {
        this.cmdFactory = cmdFactory;
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

    protected boolean isValid(boolean when, Cmd filed, boolean allNeedValue, Object... params) {
        if (!when || filed == null || params == null || params.length < 1) {
            return false;
        }
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

    protected boolean isValid(boolean when, boolean allNeedValue, Object... params) {
        if (!when || params == null || params.length < 1) {
            return false;
        }
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


    private Cmd convert(Object value) {
        Cmd v1;
        if (value instanceof Cmd) {
            v1 = (Cmd) value;
        } else {
            v1 = cmdFactory.value(value);
        }
        return v1;
    }

    private <T> Cmd convert(Getter<T> getter, int storey) {
        Cmd v1;
        if (getter instanceof Cmd) {
            v1 = (Cmd) getter;
        } else {
            v1 = cmdFactory.field(getter, storey);
        }
        return v1;
    }

    @Override
    public Condition empty(Cmd column, boolean when) {
        if (!when) {
            return null;
        }
        return Methods.eq(column, cmdFactory.value(""));
    }

    @Override
    public <T> Condition empty(Getter<T> column, int storey, boolean when) {
        if (!when) {
            return null;
        }
        return empty(convert(column, storey), when);
    }

    @Override
    public Condition notEmpty(Cmd column, boolean when) {
        if (!when) {
            return null;
        }
        return Methods.ne(column, cmdFactory.value(""));
    }

    @Override
    public <T> Condition notEmpty(Getter<T> column, int storey, boolean when) {
        if (!when) {
            return null;
        }
        return notEmpty(convert(column, storey), when);
    }

    @Override
    public Condition eq(Cmd column, Object value, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return Methods.eq(column, convert(value));
    }

    @Override
    public Condition ne(Cmd column, Object value, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return Methods.ne(column, convert(value));
    }

    @Override
    public Condition gt(Cmd column, Object value, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return Methods.gt(column, convert(value));
    }

    @Override
    public Condition gte(Cmd column, Object value, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return Methods.gte(column, convert(value));
    }

    @Override
    public Condition lt(Cmd column, Object value, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return Methods.lt(column, convert(value));
    }

    @Override
    public Condition lte(Cmd column, Object value, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return Methods.lte(column, convert(value));
    }

    @Override
    public Condition like(Cmd column, String value, LikeMode mode, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return Methods.like(column, value, mode);
    }

    @Override
    public Condition notLike(Cmd column, String value, LikeMode mode, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return Methods.notLike(column, value, mode);
    }

    @Override
    public Condition between(Cmd column, Serializable value, Serializable value2, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return Methods.between(column, value, value2);
    }

    @Override
    public Condition notBetween(Cmd column, Serializable value, Serializable value2, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return Methods.notBetween(column, value, value2);
    }

    @Override
    public Condition isNull(Cmd column, boolean when) {
        if (!isValid(when, column, true)) {
            return null;
        }
        return Methods.isNull(column);
    }

    @Override
    public Condition isNotNull(Cmd column, boolean when) {
        if (!isValid(when, column, true)) {
            return null;
        }
        return Methods.isNotNull(column);
    }

    @Override
    public <T> Condition between(Getter<T> column, Serializable value, Serializable value2, int storey, boolean when) {
        if (!isValid(when, true, value, value2)) {
            return null;
        }
        return Methods.between(convert(column, storey), value, value2);
    }

    @Override
    public <T> Condition eq(Getter<T> column, Object value, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return Methods.eq(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> Condition eq(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return Methods.eq(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> Condition gt(Getter<T> column, Object value, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return Methods.gt(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> Condition gt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return Methods.gt(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> Condition gte(Getter<T> column, Object value, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return Methods.gte(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> Condition gte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return Methods.gte(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> Condition like(Getter<T> column, String value, LikeMode mode, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return Methods.like(convert(column, storey), value, mode);
    }

    @Override
    public <T> Condition lt(Getter<T> column, Object value, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return Methods.lt(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> Condition lt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return Methods.lt(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> Condition lte(Getter<T> column, Object value, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return Methods.lte(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> Condition lte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return Methods.lte(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> Condition ne(Getter<T> column, Object value, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return Methods.ne(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> Condition ne(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return Methods.ne(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> Condition notBetween(Getter<T> column, Serializable value, Serializable value2, int storey, boolean when) {
        if (!isValid(when, true, value, value2)) {
            return null;
        }
        return Methods.notBetween(convert(column, storey), value, value2);
    }

    @Override
    public <T> Condition notLike(Getter<T> column, String value, LikeMode mode, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return Methods.notLike(convert(column, storey), value, mode);
    }

    @Override
    public <T> Condition isNull(Getter<T> column, int storey, boolean when) {
        if (!when) {
            return null;
        }
        return Methods.isNull(convert(column, storey));
    }

    @Override
    public <T> Condition isNotNull(Getter<T> column, int storey, boolean when) {
        if (!when) {
            return null;
        }
        return Methods.isNotNull(convert(column, storey));
    }

    @Override
    public Condition in(Cmd cmd, boolean when, Query query) {
        if (!isValid(when, true, query)) {
            return null;
        }
        return Methods.in(cmd, query);
    }

    @Override
    public Condition in(Cmd cmd, boolean when, Serializable... values) {
        if (!isValid(when, false, values)) {
            return null;
        }
        return Methods.in(cmd, values);
    }

    @Override
    public Condition in(Cmd cmd, boolean when, List<Serializable> values) {
        if (!isValid(when, false, values)) {
            return null;
        }
        return Methods.in(cmd, values);
    }

    @Override
    public <T> Condition in(Getter<T> column, int storey, boolean when, Query query) {
        if (!isValid(when, true, query)) {
            return null;
        }
        return Methods.in(convert(column, storey), query);
    }

    @Override
    public <T> Condition in(Getter<T> column, int storey, boolean when, Serializable... values) {
        if (!isValid(when, false, values)) {
            return null;
        }
        return Methods.in(convert(column, storey), values);
    }

    @Override
    public <T> Condition in(Getter<T> column, int storey, boolean when, List<Serializable> values) {
        if (!isValid(when, false, values)) {
            return null;
        }
        return Methods.in(convert(column, storey), values);
    }

    @Override
    public Condition exists(boolean when, Query query) {
        if (!when) {
            return null;
        }
        return Methods.exists(query);
    }


    @Override
    public Condition notExists(boolean when, Query query) {
        if (!when) {
            return null;
        }
        return Methods.notExists(query);
    }
}
