package db.sql.core.api.cmd.fun;

import db.sql.api.cmd.Cmd;
import db.sql.api.cmd.LikeMode;
import db.sql.core.api.cmd.basic.BasicValue;
import db.sql.core.api.cmd.condition.*;

import java.io.Serializable;

public interface FunctionInterface extends Cmd {

    default Max max() {
        return new Max(this);
    }

    default Min min() {
        return new Min(this);
    }

    default Avg avg() {
        return new Avg(this);
    }

    default Abs abs() {
        return new Abs(this);
    }

    default Pow pow(int n) {
        return new Pow(this, n);
    }

    default Count count() {
        return new Count(this);
    }

    default Round round(int precision) {
        return new Round(this, precision);
    }

    default Multiply multiply(Number value) {
        return new Multiply(this, value);
    }

    default Multiply multiply(Cmd value) {
        return new Multiply(this, value);
    }

    default Divide divide(Number value) {
        return new Divide(this, value);
    }

    default Divide divide(Cmd value) {
        return new Divide(this, value);
    }

    default Subtract subtract(Number value) {
        return new Subtract(this, value);
    }

    default Subtract subtract(Cmd value) {
        return new Subtract(this, value);
    }

    default Plus plus(Number value) {
        return new Plus(this, value);
    }

    default Plus plus(Cmd value) {
        return new Plus(this, value);
    }

    default Concat concat(Serializable... values) {
        return new Concat(this, values);
    }

    default Concat concat(Cmd... values) {
        return new Concat(this, values);
    }

    default ConcatAs concatAs(String split, Serializable... values) {
        return new ConcatAs(this, split, values);
    }

    default ConcatAs concatAs(String split, Cmd... values) {
        return new ConcatAs(this, split, values);
    }

    default IfNull ifNull(Object value) {
        if (value instanceof Cmd) {
            return new IfNull(this, (Cmd) value);
        }
        return new IfNull(this, new BasicValue(value));
    }

    default Eq eq(Object value) {
        if (value instanceof Cmd) {
            return new Eq(this, (Cmd) value);
        }
        return new Eq(this, new BasicValue(value));
    }

    default Gt gt(Object value) {
        if (value instanceof Cmd) {
            return new Gt(this, (Cmd) value);
        }
        return new Gt(this, new BasicValue(value));
    }

    default Gte gte(Object value) {
        if (value instanceof Cmd) {
            return new Gte(this, (Cmd) value);
        }
        return new Gte(this, new BasicValue(value));
    }

    default Lt lt(Object value) {
        if (value instanceof Cmd) {
            return new Lt(this, (Cmd) value);
        }
        return new Lt(this, new BasicValue(value));
    }

    default Lte lte(Object value) {
        if (value instanceof Cmd) {
            return new Lte(this, (Cmd) value);
        }
        return new Lte(this, new BasicValue(value));
    }

    default Ne ne(Object value) {
        if (value instanceof Cmd) {
            return new Ne(this, (Cmd) value);
        }
        return new Ne(this, new BasicValue(value));
    }

    default Between between(Serializable value1, Serializable value2) {
        return new Between(this, new BasicValue(value1), new BasicValue(value2));
    }

    default Between notBetween(Serializable value1, Serializable value2) {
        return new NotBetween(this, new BasicValue(value1), new BasicValue(value2));
    }

    default Like like(Serializable value) {
        return like(value, LikeMode.DEFAULT);
    }

    default Like like(Serializable value, LikeMode mode) {
        return new Like(this, new BasicValue(value), mode);
    }

    default NotLike notLike(Serializable value) {
        return notLike(value, LikeMode.DEFAULT);
    }

    default NotLike notLike(Serializable value, LikeMode mode) {
        return new NotLike(this, new BasicValue(value), mode);
    }

    default In in(Serializable... values) {
        return new In(this).add(values);
    }

}
