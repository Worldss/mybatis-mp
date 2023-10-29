package db.sql.core.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.Compare;
import db.sql.api.Getter;
import db.sql.api.LikeMode;

public class ConditionFaction implements Compare<Condition, Cmd, Object> {

    protected final CmdFactory cmdFactory;

    public ConditionFaction(CmdFactory cmdFactory) {
        this.cmdFactory = cmdFactory;
    }

    protected boolean hasValue(Object value) {
        if (value == null) {
            return false;
        } else if (value instanceof String) {
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
    public Condition eq(Cmd cmd, Object value, boolean when) {
        if (!isValid(when, cmd, true, value)) {
            return null;
        }
        return cmdFactory.eq(cmd, convert(value));
    }

    @Override
    public Condition ne(Cmd cmd, Object value, boolean when) {
        if (!isValid(when, cmd, true, value)) {
            return null;
        }
        return cmdFactory.ne(cmd, convert(value));
    }

    @Override
    public Condition gt(Cmd cmd, Object value, boolean when) {
        if (!isValid(when, cmd, true, value)) {
            return null;
        }
        return cmdFactory.gt(cmd, convert(value));
    }

    @Override
    public Condition gte(Cmd cmd, Object value, boolean when) {
        if (!isValid(when, cmd, true, value)) {
            return null;
        }
        return cmdFactory.gte(cmd, convert(value));
    }

    @Override
    public Condition lt(Cmd cmd, Object value, boolean when) {
        if (!isValid(when, cmd, true, value)) {
            return null;
        }
        return cmdFactory.lt(cmd, convert(value));
    }

    @Override
    public Condition lte(Cmd cmd, Object value, boolean when) {
        if (!isValid(when, cmd, true, value)) {
            return null;
        }
        return cmdFactory.lte(cmd, convert(value));
    }

    @Override
    public Condition like(Cmd cmd, Object value, LikeMode mode, boolean when) {
        if (!isValid(when, cmd, true, value)) {
            return null;
        }
        return cmdFactory.like(cmd, convert(value), mode);
    }

    @Override
    public Condition notLike(Cmd cmd, Object value, LikeMode mode, boolean when) {
        if (!isValid(when, cmd, true, value)) {
            return null;
        }
        return cmdFactory.notLike(cmd, convert(value), mode);
    }

    @Override
    public Condition between(Cmd cmd, Object value, Object value2, boolean when) {
        if (!isValid(when, cmd, true, value)) {
            return null;
        }
        return cmdFactory.between(cmd, convert(value), convert(value2));
    }

    @Override
    public Condition notBetween(Cmd cmd, Object value, Object value2, boolean when) {
        if (!isValid(when, cmd, true, value)) {
            return null;
        }
        return cmdFactory.notBetween(cmd, convert(value), convert(value2));
    }

    @Override
    public Condition isNull(Cmd cmd, boolean when) {
        if (!isValid(when, cmd, true)) {
            return null;
        }
        return cmdFactory.isNull(cmd);
    }

    @Override
    public Condition isNotNull(Cmd cmd, boolean when) {
        if (!isValid(when, cmd, true)) {
            return null;
        }
        return cmdFactory.isNotNull(cmd);
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
        if (!isValid(when, true)) {
            return null;
        }
        return cmdFactory.isNull(convert(column, storey));
    }

    @Override
    public <T> Condition isNotNull(Getter<T> column, int storey, boolean when) {
        if (!isValid(when, true)) {
            return null;
        }
        return cmdFactory.isNotNull(convert(column, storey));
    }

    public Condition in(Cmd cmd, Object[] values, boolean when) {
        if (!isValid(when, cmd, false, values)) {
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
        return cmdFactory.in(cmd).add(cmds);
    }
}
