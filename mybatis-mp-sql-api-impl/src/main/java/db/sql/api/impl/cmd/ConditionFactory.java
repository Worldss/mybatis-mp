package db.sql.api.impl.cmd;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.LikeMode;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.cmd.executor.IQuery;
import db.sql.api.cmd.executor.method.condition.IConditionMethods;
import db.sql.api.impl.exception.ConditionArrayValueEmptyException;
import db.sql.api.impl.exception.ConditionValueNullException;
import db.sql.api.impl.tookit.SqlConst;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public class ConditionFactory implements IConditionMethods<ICondition, Cmd, Object> {

    protected final CmdFactory cmdFactory;
    private boolean isIgnoreEmpty = false;
    private boolean isIgnoreNull = false;
    private boolean isStringTrim = false;

    public ConditionFactory(CmdFactory cmdFactory) {
        this.cmdFactory = cmdFactory;
    }

    public CmdFactory getCmdFactory() {
        return cmdFactory;
    }

    public boolean isIgnoreEmpty() {
        return isIgnoreEmpty;
    }

    public void setIgnoreEmpty(boolean isIgnoreEmpty) {
        this.isIgnoreEmpty = isIgnoreEmpty;
    }


    public boolean isIgnoreNull() {
        return isIgnoreNull;
    }

    public void setIgnoreNull(boolean isIgnoreNull) {
        this.isIgnoreNull = isIgnoreNull;
    }


    public boolean isStringTrim() {
        return isStringTrim;
    }

    public void setStringTrim(boolean isStringTrim) {
        this.isStringTrim = isStringTrim;
    }

    protected boolean isKeyValid(Cmd filed) {
        return filed != null;
    }

    private Object getSingleValue(Object value) {
        if (Objects.isNull(value)) {
            if (!isIgnoreNull()) {
                throw new ConditionValueNullException("条件参数里包含null值");
            }
            return null;
        }
        if (value instanceof String) {
            String str = (String) value;
            str = isStringTrim() ? str.trim() : str;
            if (isIgnoreEmpty() && SqlConst.EMPTY.equals(str)) {
                return null;
            }
            return str;
        }
        return value;
    }

    protected Object checkAndGetValidValue(Object value) {
        if (Objects.isNull(value)) {
            if (!isIgnoreNull()) {
                throw new ConditionValueNullException("条件参数里包含null值");
            }
            return null;
        }
        if (value instanceof Object[]) {
            Object[] values = (Object[]) value;
            List<Object> objectList = new ArrayList<>(values.length);
            for (Object v : values) {
                Object nv = getSingleValue(v);
                if (Objects.isNull(nv)) {
                    continue;
                }
                objectList.add(nv);
            }

            if (objectList.isEmpty()) {
                throw new ConditionArrayValueEmptyException("array can't be empty");
            }
            int length = objectList.size();
            if (length == values.length) {
                for (int i = 0; i < length; i++) {
                    values[i] = objectList.get(i);
                }
                objectList.clear();
                return values;
            }

            Object[] newObject = (Object[]) Array.newInstance(value.getClass().getComponentType(), length);
            for (int i = 0; i < length; i++) {
                newObject[i] = objectList.get(i);
            }
            return newObject;
        } else if (value instanceof Collection) {
            Collection collection = (Collection) value;
            Collection<Object> objectList;
            if (value instanceof List) {
                objectList = new ArrayList<>(collection.size());
            } else if (value instanceof Set) {
                objectList = new HashSet<>(collection.size());
            } else if (value instanceof Queue) {
                objectList = new ArrayDeque<>(collection.size());
            } else {
                throw new RuntimeException("Not supported");
            }
            for (Object v : collection) {
                Object nv = getSingleValue(v);
                if (Objects.isNull(nv)) {
                    continue;
                }
                objectList.add(nv);
            }
            if (objectList.isEmpty()) {
                throw new ConditionArrayValueEmptyException("collection can't be empty");
            }
            return collection;
        }
        return getSingleValue(value);
    }

    private Cmd convertToCmd(Object value) {
        Cmd v1;
        if (value instanceof Cmd) {
            v1 = (Cmd) value;
        } else {
            if (isStringTrim() && value instanceof String) {
                String str = (String) value;
                value = str.trim();
            }
            v1 = cmdFactory.value(value);
        }
        return v1;
    }

    private <T> Cmd convertToCmd(Getter<T> column, int storey) {
        return cmdFactory.field(column, storey);
    }

    @Override
    public ICondition empty(boolean when, Cmd column) {
        if (!when) {
            return null;
        }
        if (!isKeyValid(column)) {
            return null;
        }
        return Methods.eq(column, cmdFactory.value(""));
    }

    @Override
    public <T> ICondition empty(boolean when, Getter<T> column, int storey) {
        if (!when) {
            return null;
        }
        return Methods.eq(convertToCmd(column, storey), cmdFactory.value(""));
    }

    @Override
    public ICondition notEmpty(boolean when, Cmd column) {
        if (!when) {
            return null;
        }
        if (!isKeyValid(column)) {
            return null;
        }
        return Methods.ne(column, cmdFactory.value(""));
    }

    @Override
    public <T> ICondition notEmpty(boolean when, Getter<T> column, int storey) {
        if (!when) {
            return null;
        }
        return Methods.ne(convertToCmd(column, storey), cmdFactory.value(""));
    }

    @Override
    public ICondition eq(boolean when, Cmd column, Object value) {
        if (!when) {
            return null;
        }
        if (!isKeyValid(column)) {
            return null;
        }
        value = checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.eq(column, convertToCmd(value));
    }

    @Override
    public ICondition ne(boolean when, Cmd column, Object value) {
        if (!when) {
            return null;
        }
        if (!isKeyValid(column)) {
            return null;
        }
        value = checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.ne(column, convertToCmd(value));
    }

    @Override
    public ICondition gt(boolean when, Cmd column, Object value) {
        if (!when) {
            return null;
        }
        if (!isKeyValid(column)) {
            return null;
        }
        value = checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.gt(column, convertToCmd(value));
    }

    @Override
    public ICondition gte(boolean when, Cmd column, Object value) {
        if (!when) {
            return null;
        }
        if (!isKeyValid(column)) {
            return null;
        }
        value = checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.gte(column, convertToCmd(value));
    }

    @Override
    public ICondition lt(boolean when, Cmd column, Object value) {
        if (!when) {
            return null;
        }
        if (!isKeyValid(column)) {
            return null;
        }
        value = checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.lt(column, convertToCmd(value));
    }

    @Override
    public ICondition lte(boolean when, Cmd column, Object value) {
        if (!when) {
            return null;
        }
        if (!isKeyValid(column)) {
            return null;
        }
        value = checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.lte(column, convertToCmd(value));
    }

    @Override
    public ICondition like(boolean when, LikeMode mode, Cmd column, String value) {
        if (!when) {
            return null;
        }
        if (!isKeyValid(column)) {
            return null;
        }
        value = (String) checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.like(column, value, mode);
    }

    @Override
    public ICondition notLike(boolean when, LikeMode mode, Cmd column, String value) {
        if (!when) {
            return null;
        }
        if (!isKeyValid(column)) {
            return null;
        }
        value = (String) checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.notLike(column, value, mode);
    }

    @Override
    public ICondition between(boolean when, Cmd column, Serializable value, Serializable value2) {
        if (!when) {
            return null;
        }
        if (!isKeyValid(column)) {
            return null;
        }
        value = (Serializable) checkAndGetValidValue(value);
        value2 = (Serializable) checkAndGetValidValue(value2);
        if (Objects.isNull(value) || Objects.isNull(value2)) {
            if (Objects.isNull(value) && Objects.isNull(value2)) {
                return null;
            }
            throw new ConditionValueNullException("条件参数里包含null值");
        }
        return Methods.between(column, value, value2);
    }

    @Override
    public <T> ICondition between(boolean when, Getter<T> column, int storey, Serializable value, Serializable value2) {
        if (!when) {
            return null;
        }

        value = (Serializable) checkAndGetValidValue(value);
        value2 = (Serializable) checkAndGetValidValue(value2);
        if (Objects.isNull(value) || Objects.isNull(value2)) {
            if (Objects.isNull(value) && Objects.isNull(value2)) {
                return null;
            }
            throw new ConditionValueNullException("条件参数里包含null值");
        }
        return Methods.between(convertToCmd(column, storey), value, value2);
    }

    @Override
    public ICondition notBetween(boolean when, Cmd column, Serializable value, Serializable value2) {
        if (!when) {
            return null;
        }
        if (!isKeyValid(column)) {
            return null;
        }
        value = (Serializable) checkAndGetValidValue(value);
        value2 = (Serializable) checkAndGetValidValue(value2);
        if (Objects.isNull(value) || Objects.isNull(value2)) {
            if (Objects.isNull(value) && Objects.isNull(value2)) {
                return null;
            }
            throw new ConditionValueNullException("条件参数里包含null值");
        }
        return Methods.notBetween(column, value, value2);
    }

    @Override
    public <T> ICondition notBetween(boolean when, Getter<T> column, int storey, Serializable value, Serializable value2) {
        if (!when) {
            return null;
        }

        value = (Serializable) checkAndGetValidValue(value);
        value2 = (Serializable) checkAndGetValidValue(value2);
        if (Objects.isNull(value) || Objects.isNull(value2)) {
            if (Objects.isNull(value) && Objects.isNull(value2)) {
                return null;
            }
            throw new ConditionValueNullException("条件参数里包含null值");
        }
        return Methods.notBetween(convertToCmd(column, storey), value, value2);
    }

    @Override
    public ICondition isNull(boolean when, Cmd column) {
        if (!when) {
            return null;
        }
        if (!isKeyValid(column)) {
            return null;
        }
        return Methods.isNull(column);
    }

    @Override
    public ICondition isNotNull(boolean when, Cmd column) {
        if (!when) {
            return null;
        }
        if (!isKeyValid(column)) {
            return null;
        }
        return Methods.isNotNull(column);
    }

    @Override
    public <T> ICondition eq(boolean when, Getter<T> column, int storey, Object value) {
        if (!when) {
            return null;
        }
        value = checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.eq(convertToCmd(column, storey), convertToCmd(value));
    }

    @Override
    public <T, T2> ICondition eq(boolean when, Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        if (!when) {
            return null;
        }
        return Methods.eq(convertToCmd(column, columnStorey), convertToCmd(value, valueStorey));
    }

    @Override
    public <T> ICondition gt(boolean when, Getter<T> column, int storey, Object value) {
        if (!when) {
            return null;
        }
        value = checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.gt(convertToCmd(column, storey), convertToCmd(value));
    }

    @Override
    public <T, T2> ICondition gt(boolean when, Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        if (!when) {
            return null;
        }
        return Methods.gt(convertToCmd(column, columnStorey), convertToCmd(value, valueStorey));
    }

    @Override
    public <T> ICondition gte(boolean when, Getter<T> column, int storey, Object value) {
        if (!when) {
            return null;
        }
        value = checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.gte(convertToCmd(column, storey), convertToCmd(value));
    }

    @Override
    public <T, T2> ICondition gte(boolean when, Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        if (!when) {
            return null;
        }
        return Methods.gte(convertToCmd(column, columnStorey), convertToCmd(value, valueStorey));
    }

    @Override
    public <T> ICondition like(boolean when, LikeMode mode, Getter<T> column, int storey, String value) {
        if (!when) {
            return null;
        }
        value = (String) checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.like(convertToCmd(column, storey), value, mode);
    }

    @Override
    public <T> ICondition lt(boolean when, Getter<T> column, int storey, Object value) {
        if (!when) {
            return null;
        }
        value = checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.lt(convertToCmd(column, storey), convertToCmd(value));
    }

    @Override
    public <T, T2> ICondition lt(boolean when, Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        if (!when) {
            return null;
        }
        return Methods.lt(convertToCmd(column, columnStorey), convertToCmd(value, valueStorey));
    }

    @Override
    public <T> ICondition lte(boolean when, Getter<T> column, int storey, Object value) {
        if (!when) {
            return null;
        }
        value = checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.lte(convertToCmd(column, storey), convertToCmd(value));
    }

    @Override
    public <T, T2> ICondition lte(boolean when, Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        if (!when) {
            return null;
        }
        return Methods.lte(convertToCmd(column, columnStorey), convertToCmd(value, valueStorey));
    }

    @Override
    public <T> ICondition ne(boolean when, Getter<T> column, int storey, Object value) {
        if (!when) {
            return null;
        }
        value = checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.ne(convertToCmd(column, storey), convertToCmd(value));
    }

    @Override
    public <T, T2> ICondition ne(boolean when, Getter<T> column, int columnStorey, Getter<T2> value, int valueStorey) {
        if (!when) {
            return null;
        }
        return Methods.ne(convertToCmd(column, columnStorey), convertToCmd(value, valueStorey));
    }


    @Override
    public <T> ICondition notLike(boolean when, LikeMode mode, Getter<T> column, int storey, String value) {
        if (!when) {
            return null;
        }
        value = (String) checkAndGetValidValue(value);
        if (Objects.isNull(value)) {
            return null;
        }
        return Methods.notLike(convertToCmd(column, storey), value, mode);
    }

    @Override
    public <T> ICondition isNull(boolean when, Getter<T> column, int storey) {
        if (!when) {
            return null;
        }
        return Methods.isNull(convertToCmd(column, storey));
    }

    @Override
    public <T> ICondition isNotNull(boolean when, Getter<T> column, int storey) {
        if (!when) {
            return null;
        }
        return Methods.isNotNull(convertToCmd(column, storey));
    }

    @Override
    public ICondition in(boolean when, Cmd column, IQuery query) {
        if (!when) {
            return null;
        }
        Objects.requireNonNull(query);
        return Methods.in(column, query);
    }

    @Override
    public ICondition in(boolean when, Cmd column, Serializable... values) {
        if (!when) {
            return null;
        }
        values = (Serializable[]) checkAndGetValidValue(values);
        if (Objects.isNull(values)) {
            return null;
        }
        return Methods.in(column, values);
    }

    @Override
    public ICondition in(boolean when, Cmd column, List<Serializable> values) {
        if (!when) {
            return null;
        }
        values = (List<Serializable>) checkAndGetValidValue(values);
        if (Objects.isNull(values)) {
            return null;
        }
        return Methods.in(column, values);
    }

    @Override
    public <T> ICondition in(boolean when, Getter<T> column, int storey, IQuery query) {
        if (!when) {
            return null;
        }
        Objects.requireNonNull(query);
        return Methods.in(convertToCmd(column, storey), query);
    }

    @Override
    public <T> ICondition in(boolean when, Getter<T> column, int storey, Serializable[] values) {
        if (!when) {
            return null;
        }
        values = (Serializable[]) checkAndGetValidValue(values);
        if (Objects.isNull(values)) {
            return null;
        }
        return Methods.in(convertToCmd(column, storey), values);
    }

    @Override
    public <T> ICondition in(boolean when, Getter<T> column, int storey, List<Serializable> values) {
        if (!when) {
            return null;
        }
        values = (List<Serializable>) checkAndGetValidValue(values);
        if (Objects.isNull(values)) {
            return null;
        }
        return Methods.in(convertToCmd(column, storey), values);
    }

    @Override
    public ICondition exists(boolean when, IQuery query) {
        if (!when) {
            return null;
        }
        Objects.requireNonNull(query);
        return Methods.exists(query);
    }


    @Override
    public ICondition notExists(boolean when, IQuery query) {
        if (!when) {
            return null;
        }
        Objects.requireNonNull(query);
        return Methods.notExists(query);
    }
}
