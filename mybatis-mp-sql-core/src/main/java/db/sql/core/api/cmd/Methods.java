package db.sql.core.api.cmd;

import db.sql.api.cmd.Cmd;
import db.sql.api.cmd.LikeMode;
import db.sql.api.cmd.executor.Query;
import db.sql.core.api.cmd.basic.Condition;
import db.sql.core.api.cmd.condition.*;
import db.sql.core.api.cmd.fun.*;
import db.sql.core.api.tookit.SqlConst;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库方法集合
 */
public class Methods {

    /**
     * plus加法
     *
     * @param key
     * @param value
     * @return
     */
    public static Plus plus(Cmd key, Number value) {
        return new Plus(key, value);
    }

    /**
     * plus加法
     *
     * @param key
     * @param value
     * @return
     */
    public static Plus plus(Cmd key, Cmd value) {
        return new Plus(key, value);
    }

    /**
     * subtract加法
     *
     * @param key
     * @param value
     * @return
     */
    public static Subtract subtract(Cmd key, Number value) {
        return new Subtract(key, value);
    }

    /**
     * subtract减法
     *
     * @param key
     * @param value
     * @return
     */
    public static Subtract subtract(Cmd key, Cmd value) {
        return new Subtract(key, value);
    }

    /**
     * multiply乘法
     *
     * @param key
     * @param value
     * @return
     */
    public static Multiply multiply(Cmd key, Number value) {
        return new Multiply(key, value);
    }

    /**
     * divide除法
     *
     * @param key
     * @param value
     * @return
     */
    public static Divide divide(Cmd key, Cmd value) {
        return new Divide(key, value);
    }

    /**
     * multiply乘法
     *
     * @param key
     * @param value
     * @return
     */
    public static Divide divide(Cmd key, Number value) {
        return new Divide(key, value);
    }

    /**
     * multiply乘法
     *
     * @param key
     * @param value
     * @return
     */
    public static Multiply multiply(Cmd key, Cmd value) {
        return new Multiply(key, value);
    }

    /**
     * count条数 函数
     *
     * @param key
     * @return
     */
    public static Count count(Cmd key) {
        return new Count(key);
    }

    /**
     * min最小 函数
     *
     * @param key
     * @return
     */
    public static Min min(Cmd key) {
        return new Min(key);
    }

    /**
     * max最大 函数
     *
     * @param key
     * @return
     */
    public static Max max(Cmd key) {
        return new Max(key);
    }

    /**
     * avg平局值 函数
     *
     * @param key
     * @return
     */
    public static Avg avg(Cmd key) {
        return new Avg(key);
    }

    /**
     * abs绝对值 函数
     *
     * @param key
     * @return
     */
    public static Abs abs(Cmd key) {
        return new Abs(key);
    }

    /**
     * pow平方 函数
     *
     * @param key
     * @param n
     * @return
     */
    public static Pow pow(Cmd key, int n) {
        return new Pow(key, n);
    }

    /**
     * round四舍五入 函数
     *
     * @param key
     * @param precision 精度
     * @return
     */
    public static Round round(Cmd key, int precision) {
        return new Round(key, precision);
    }

    /**
     * concat拼接 函数
     *
     * @param key
     * @param values 数据
     * @return
     */
    public static Concat concat(Cmd key, Serializable... values) {
        return new Concat(key, values);
    }

    /**
     * concat拼接 函数
     *
     * @param key
     * @param values 数据
     * @return
     */
    public static Concat concat(Cmd key, Cmd... values) {
        return new Concat(key, values);
    }

    /**
     * concat拼接 函数
     *
     * @param key
     * @param values 数据
     * @return
     */
    public static Concat concat(Cmd key, Object... values) {
        return new Concat(key, values);
    }

    /**
     * concatAs拼接 函数
     *
     * @param key
     * @param split
     * @param values
     * @return
     */
    public static ConcatAs concatAs(Cmd key, String split, Serializable... values) {
        return new ConcatAs(key, split, values);
    }

    /**
     * concatAs拼接 函数
     *
     * @param key
     * @param split
     * @param values
     * @return
     */
    public static ConcatAs concatAs(Cmd key, String split, Cmd... values) {
        return new ConcatAs(key, split, values);
    }

    /**
     * concatAs拼接 函数
     *
     * @param key
     * @param split
     * @param values
     * @return
     */
    public static ConcatAs concatAs(Cmd key, String split, Object... values) {
        return new ConcatAs(key, split, values);
    }

    /**
     * if(条件,值1,值2) 函数
     *
     * @param condition
     * @param value
     * @param thenValue
     * @return
     */
    public static If if_(Condition condition, Serializable value, Serializable thenValue) {
        return new If(condition, value, thenValue);
    }

    /**
     * if(条件,值1,值2) 函数
     *
     * @param condition
     * @param value
     * @param thenValue
     * @return
     */
    public static If if_(Condition condition, Cmd value, Serializable thenValue) {
        return new If(condition, value, thenValue);
    }

    /**
     * if(条件,值1,值2) 函数
     *
     * @param condition
     * @param value
     * @param thenValue
     * @return
     */
    public static If if_(Condition condition, Serializable value, Cmd thenValue) {
        return new If(condition, value, thenValue);
    }

    /**
     * IF(条件,值1,值2) 函数
     *
     * @param condition
     * @param value
     * @param thenValue
     * @return
     */
    public static If if_(Condition condition, Cmd value, Cmd thenValue) {
        return new If(condition, value, thenValue);
    }

    /**
     * IFNULL(条件,值1,值2) 函数
     *
     * @param condition
     * @param value
     * @return
     */
    public static IfNull ifNull(Condition condition, Cmd value) {
        return new IfNull(condition, value);
    }

    /**
     * IFNULL(条件,值1,值2) 函数
     *
     * @param condition
     * @param value
     * @return
     */
    public static IfNull ifNull(Condition condition, Serializable value) {
        return new IfNull(condition, value);
    }

    /**
     * key列 is NOT NULL
     *
     * @param key
     * @return
     */
    public static IsNull isNull(Cmd key) {
        return new IsNull(key);
    }

    /**
     * key列 is NOT NULL
     *
     * @param key
     * @return
     */
    public static IsNotNull isNotNull(Cmd key) {
        return new IsNotNull(key);
    }

    /**
     * key列 为空
     *
     * @param key
     * @return
     */
    public static Eq isEmpty(Cmd key) {
        return new Eq(key, SqlConst.EMPTY);
    }

    /**
     * key列 不为空
     *
     * @param key
     * @return
     */
    public static Ne isNotEmpty(Cmd key) {
        return new Ne(key, SqlConst.EMPTY);
    }

    /**
     * case 语句块
     *
     * @return
     */
    public static Case case_() {
        return new Case();
    }

    /* --------------------------------------以下为判断条件----------------------------------------------*/

    /**
     * eq等于 判断
     *
     * @return
     */
    public static Eq eq(Cmd key, Serializable value) {
        return new Eq(key, value);
    }

    /**
     * eq等于 判断
     *
     * @return
     */
    public static Eq eq(Cmd key, Cmd value) {
        return new Eq(key, value);
    }

    /**
     * ne不等于 判断
     *
     * @return
     */
    public static Ne ne(Cmd key, Serializable value) {
        return new Ne(key, value);
    }

    /**
     * ne不等于 判断
     *
     * @return
     */
    public static Ne ne(Cmd key, Cmd value) {
        return new Ne(key, value);
    }

    /**
     * gt大于 判断
     *
     * @return
     */
    public static Gt gt(Cmd key, Serializable value) {
        return new Gt(key, value);
    }

    /**
     * gt大于 判断
     *
     * @return
     */
    public static Gt gt(Cmd key, Cmd value) {
        return new Gt(key, value);
    }

    /**
     * gte大于等于 判断
     *
     * @return
     */
    public static Gte gte(Cmd key, Serializable value) {
        return new Gte(key, value);
    }

    /**
     * gte大于等于 判断
     *
     * @return
     */
    public static Gte gte(Cmd key, Cmd value) {
        return new Gte(key, value);
    }

    /**
     * gt小于 判断
     *
     * @return
     */
    public static Lt lt(Cmd key, Serializable value) {
        return new Lt(key, value);
    }

    /**
     * gt小于 判断
     *
     * @return
     */
    public static Lt lt(Cmd key, Cmd value) {
        return new Lt(key, value);
    }

    /**
     * gt小于等于 判断
     *
     * @return
     */
    public static Lte lte(Cmd key, Serializable value) {
        return new Lte(key, value);
    }

    /**
     * gt小于等于 判断
     *
     * @return
     */
    public static Lte lte(Cmd key, Cmd value) {
        return new Lte(key, value);
    }

    /**
     * in 多个值
     *
     * @return
     */
    public static In in(Cmd key, Serializable... values) {
        return new In(key).add(values);
    }

    /**
     * in 多个值
     *
     * @return
     */
    public static In in(Cmd key, List<Serializable> values) {
        return new In(key).add(values);
    }

    /**
     * in 一个查询
     *
     * @return
     */
    public static In in(Cmd key, Query query) {
        return new In(key).add(query);
    }

    /**
     * exists 一个查询
     *
     * @param query
     * @return
     */
    public static Exists exists(Query query) {
        return new Exists(query);
    }

    /**
     * between 区间判断
     *
     * @param key
     * @param value
     * @param value2
     * @return
     */
    public static Between between(Cmd key, Serializable value, Serializable value2) {
        return new Between(key, value, value2);
    }

    /**
     * not between 区间判断
     *
     * @param key
     * @param value
     * @param value2
     * @return
     */
    public static NotBetween notBetween(Cmd key, Serializable value, Serializable value2) {
        return new NotBetween(key, value, value2);
    }

    /**
     * like 判断
     *
     * @param key
     * @param value
     * @return
     */
    public static Like like(Cmd key, String value) {
        return new Like(key, value);
    }

    /**
     * like 判断
     *
     * @param key
     * @param value
     * @return
     */
    public static Like like(Cmd key, String value, LikeMode mode) {
        return new Like(key, value, mode);
    }

    /**
     * not like 判断
     *
     * @param key
     * @param value
     * @return
     */
    public static NotLike notLike(Cmd key, String value) {
        return new NotLike(key, value);
    }

    /**
     * not like 判断
     *
     * @param key
     * @param value
     * @return
     */
    public static NotLike notLike(Cmd key, String value, LikeMode mode) {
        return new NotLike(key, value, mode);
    }
}
