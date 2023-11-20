package db.sql.core.api.cmd;

import db.sql.api.Getter;
import db.sql.api.cmd.Cmd;
import db.sql.api.cmd.LikeMode;
import db.sql.api.cmd.basic.Condition;
import db.sql.api.cmd.executor.method.compare.Compare;

import java.util.List;

public class ConditionFaction implements Compare<Condition, Cmd, Object> {

    protected final CmdFactory cmdFactory;

    public ConditionFaction(CmdFactory cmdFactory) {
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
        return cmdFactory.eq(column, cmdFactory.value(""));
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
        return cmdFactory.ne(column, cmdFactory.value(""));
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
        return cmdFactory.eq(column, convert(value));
    }

    @Override
    public Condition ne(Cmd column, Object value, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return cmdFactory.ne(column, convert(value));
    }

    @Override
    public Condition gt(Cmd column, Object value, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return cmdFactory.gt(column, convert(value));
    }

    @Override
    public Condition gte(Cmd column, Object value, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return cmdFactory.gte(column, convert(value));
    }

    @Override
    public Condition lt(Cmd column, Object value, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return cmdFactory.lt(column, convert(value));
    }

    @Override
    public Condition lte(Cmd column, Object value, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return cmdFactory.lte(column, convert(value));
    }

    @Override
    public Condition like(Cmd column, Object value, LikeMode mode, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return cmdFactory.like(column, convert(value), mode);
    }

    @Override
    public Condition notLike(Cmd column, Object value, LikeMode mode, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return cmdFactory.notLike(column, convert(value), mode);
    }

    @Override
    public Condition between(Cmd column, Object value, Object value2, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return cmdFactory.between(column, convert(value), convert(value2));
    }

    @Override
    public Condition notBetween(Cmd column, Object value, Object value2, boolean when) {
        if (!isValid(when, column, true, value)) {
            return null;
        }
        return cmdFactory.notBetween(column, convert(value), convert(value2));
    }

    @Override
    public Condition isNull(Cmd column, boolean when) {
        if (!isValid(when, column, true)) {
            return null;
        }
        return cmdFactory.isNull(column);
    }

    @Override
    public Condition isNotNull(Cmd column, boolean when) {
        if (!isValid(when, column, true)) {
            return null;
        }
        return cmdFactory.isNotNull(column);
    }

    @Override
    public <T> Condition between(Getter<T> column, Object value, Object value2, int storey, boolean when) {
        if (!isValid(when, true, value, value2)) {
            return null;
        }
        return cmdFactory.between(convert(column, storey), convert(value), convert(value2));
    }

    @Override
    public <T> Condition eq(Getter<T> column, Object value, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return cmdFactory.eq(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> Condition eq(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return cmdFactory.eq(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> Condition gt(Getter<T> column, Object value, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return cmdFactory.gt(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> Condition gt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return cmdFactory.gt(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> Condition gte(Getter<T> column, Object value, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return cmdFactory.gte(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> Condition gte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return cmdFactory.gte(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> Condition like(Getter<T> column, Object value, LikeMode mode, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return cmdFactory.like(convert(column, storey), convert(value), mode);
    }

    @Override
    public <T> Condition lt(Getter<T> column, Object value, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return cmdFactory.lt(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> Condition lt(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return cmdFactory.lt(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> Condition lte(Getter<T> column, Object value, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return cmdFactory.lte(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> Condition lte(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return cmdFactory.lte(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> Condition ne(Getter<T> column, Object value, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return cmdFactory.ne(convert(column, storey), convert(value));
    }

    @Override
    public <T, T2> Condition ne(Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return cmdFactory.ne(convert(column, columnStorey), convert(value, valueStorey));
    }

    @Override
    public <T> Condition notBetween(Getter<T> column, Object value, Object value2, int storey, boolean when) {
        if (!isValid(when, true, value, value2)) {
            return null;
        }
        return cmdFactory.notBetween(convert(column, storey), convert(value), convert(value2));
    }

    @Override
    public <T> Condition notLike(Getter<T> column, Object value, LikeMode mode, int storey, boolean when) {
        if (!isValid(when, true, value)) {
            return null;
        }
        return cmdFactory.notLike(convert(column, storey), convert(value), mode);
    }

    @Override
    public <T> Condition isNull(Getter<T> column, int storey, boolean when) {
        if (!when) {
            return null;
        }
        return cmdFactory.isNull(convert(column, storey));
    }

    @Override
    public <T> Condition isNotNull(Getter<T> column, int storey, boolean when) {
        if (!when) {
            return null;
        }
        return cmdFactory.isNotNull(convert(column, storey));
    }

    public <T> Condition in(Getter<T> column, Object[] values, boolean when) {
        if (!isValid(when, false, values)) {
            return null;
        }

        Cmd[] cmds = new Cmd[values.length];
        int i = 0;
        for (Object value : values) {
            if (!hasValue(value)) {
                continue;
            }
            cmds[i++] = convert(value);
        }
        return cmdFactory.in(convert(column, 1)).add(cmds);
    }

    public Condition in(Cmd column, Object[] values, boolean when) {
        if (!isValid(when, column, false, values)) {
            return null;
        }
        Cmd[] cmds = new Cmd[values.length];
        int i = 0;
        for (Object value : values) {
            if (!hasValue(value)) {
                continue;
            }
            cmds[i++] = convert(value);
        }
        return cmdFactory.in(column).add(cmds);
    }


    public <T> Condition in(Getter<T> column, List<Object> values, boolean when) {
        if (!isValid(when, false, values)) {
            return null;
        }

        Cmd[] cmds = new Cmd[values.size()];
        int i = 0;
        for (Object value : values) {
            if (!hasValue(value)) {
                continue;
            }
            cmds[i++] = convert(value);
        }
        return cmdFactory.in(convert(column, 1)).add(cmds);
    }

    public Condition in(Cmd column, List<Object> values, boolean when) {
        if (!isValid(when, column, false, values)) {
            return null;
        }
        Cmd[] cmds = new Cmd[values.size()];
        int i = 0;
        for (Object value : values) {
            if (!hasValue(value)) {
                continue;
            }
            cmds[i++] = convert(value);
        }
        return cmdFactory.in(column).add(cmds);
    }

    public Condition exists(Cmd existsCmd) {
        return this.exists(existsCmd, true);
    }

    public Condition exists(Cmd existsCmd, boolean when) {
        if (!when) {
            return null;
        }
        return cmdFactory.exists(existsCmd);
    }
}
