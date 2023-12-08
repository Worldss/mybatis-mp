package db.sql.api.impl.cmd;

import db.sql.api.Cmd;
import db.sql.api.cmd.LikeMode;
import db.sql.api.cmd.executor.IQuery;
import db.sql.api.impl.cmd.basic.BasicValue;
import db.sql.api.impl.cmd.basic.Condition;
import db.sql.api.impl.cmd.condition.*;
import db.sql.api.impl.cmd.dbFun.*;
import db.sql.api.impl.tookit.SqlConst;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 数据库方法集合
 */
public class Methods {

    /**
     * value
     *
     * @param value
     * @return
     */
    public static Cmd convert(Object value) {
        if (value instanceof Cmd) {
            return (Cmd) value;
        }
        return new BasicValue(value);
    }

    /**
     * value
     *
     * @param value
     * @return
     */
    public static Cmd convert(Serializable value) {
        return new BasicValue(value);
    }

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
     * sum求和 函数
     *
     * @param key
     * @return
     */
    public static Sum sum(Cmd key) {
        return new Sum(key);
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
     * round四舍五入 取整数位 函数
     *
     * @param key
     * @return
     */
    public static Round round(Cmd key) {
        return round(key, 0);
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
     * ceil返回大于或等于 x 的最小整数（向上取整） 函数
     *
     * @param key
     * @return
     */
    public static Ceil ceil(Cmd key) {
        return new Ceil(key);
    }

    /**
     * floor返回小于或等于 x 的最大整数（向下取整） 函数
     *
     * @param key
     * @return
     */
    public static Floor floor(Cmd key) {
        return new Floor(key);
    }

    /**
     * rand返回 0~1 的随机数 函数
     *
     * @param key
     * @return
     */
    public static Rand rand(Cmd key) {
        return new Rand(key);
    }

    /**
     * rand返回 0~max 的随机数 函数
     *
     * @param key
     * @param max
     * @return
     */
    public static Rand rand(Cmd key, Number max) {
        return new Rand(key, max);
    }

    /**
     * sign 返回 key 的符号，key 是负数、0、正数分别返回 -1、0、1 函数
     *
     * @param key
     * @return
     */
    public static Sign sign(Cmd key) {
        return new Sign(key);
    }

    /**
     * pi 返回圆周率 函数
     *
     * @return
     */
    public static Pi pi() {
        return new Pi();
    }

    /**
     * 返回数值 key 整数位 函数
     *
     * @param key
     * @return
     */
    public static Truncate truncate(Cmd key) {
        return truncate(key, 0);
    }

    /**
     * 返回数值 key 保留到小数点后 precision 位的值 函数
     *
     * @param key
     * @param precision
     * @return
     */
    public static Truncate truncate(Cmd key, int precision) {
        return new Truncate(key, precision);
    }

    /**
     * sqrt 平方根 函数
     *
     * @param key
     * @return
     */
    public static Sqrt sqrt(Cmd key) {
        return new Sqrt(key);
    }

    /**
     * mod 取模 函数
     *
     * @param key
     * @return
     */
    public static Mod mod(Cmd key, Number number) {
        return new Mod(key, number);
    }

    /**
     * 返回 e 的 key 次方 函数
     *
     * @param key
     * @return
     */
    public static Exp exp(Cmd key) {
        return new Exp(key);
    }


    /**
     * 返回自然对数（以 e 为底的对数） 函数
     *
     * @param key
     * @return
     */
    public static Log log(Cmd key) {
        return new Log(key);
    }

    /**
     * 返回以 2 为底的对数 函数
     *
     * @param key
     * @return
     */
    public static Log2 log2(Cmd key) {
        return new Log2(key);
    }

    /**
     * 返回以 10 为底的对数 函数
     *
     * @param key
     * @return
     */
    public static Log10 log10(Cmd key) {
        return new Log10(key);
    }

    /**
     * 将弧度转换为角度 函数
     *
     * @param key
     * @return
     */
    public static Degrees degrees(Cmd key) {
        return new Degrees(key);
    }

    /**
     * 将角度转换为弧度 函数
     *
     * @param key
     * @return
     */
    public static Radians radians(Cmd key) {
        return new Radians(key);
    }

    /**
     * 求正弦值 函数
     *
     * @param key
     * @return
     */
    public static Sin sin(Cmd key) {
        return new Sin(key);
    }

    /**
     * 求反正弦值 函数
     *
     * @param key
     * @return
     */
    public static Asin asin(Cmd key) {
        return new Asin(key);
    }

    /**
     * 求余弦值 函数
     *
     * @param key
     * @return
     */
    public static Cos cos(Cmd key) {
        return new Cos(key);
    }

    /**
     * 求反余弦值 函数
     *
     * @param key
     * @return
     */
    public static Acos acos(Cmd key) {
        return new Acos(key);
    }

    /**
     * 求正切值 函数
     *
     * @param key
     * @return
     */
    public static Tan tan(Cmd key) {
        return new Tan(key);
    }

    /**
     * 求反正切值 函数
     *
     * @param key
     * @return
     */
    public static Atan atan(Cmd key) {
        return new Atan(key);
    }

    /**
     * 求余切值 函数
     *
     * @param key
     * @return
     */
    public static Cot cot(Cmd key) {
        return new Cot(key);
    }

    /**
     * 返回字符串的字符数
     *
     * @param key
     * @return
     */
    public static CharLength charLength(Cmd key) {
        return new CharLength(key);
    }

    /**
     * 返回字符串的长度 函数
     *
     * @param key
     * @return
     */
    public static Length length(Cmd key) {
        return new Length(key);
    }

    /**
     * 转换成大写 函数
     *
     * @param key
     * @return
     */
    public static Upper upper(Cmd key) {
        return new Upper(key);
    }

    /**
     * 转换成小写 函数
     *
     * @param key
     * @return
     */
    public static Lower lower(Cmd key) {
        return new Lower(key);
    }

    /**
     * 左边截取
     *
     * @param key
     * @return
     */
    public static Left left(Cmd key, int length) {
        return new Left(key, length);
    }

    /**
     * 右边截取
     *
     * @param key
     * @return
     */
    public static Right right(Cmd key, int length) {
        return new Right(key, length);
    }

    /**
     * 从左边开始填充
     *
     * @param key
     * @param length
     * @param pad
     * @return
     */
    public static Lpad lpad(Cmd key, int length, String pad) {
        return new Lpad(key, length, pad);
    }

    /**
     * 从左边开始填充
     *
     * @param key
     * @param length
     * @param pad
     * @return
     */
    public static Rpad rpad(Cmd key, int length, String pad) {
        return new Rpad(key, length, pad);
    }

    /**
     * 删除两边空格
     *
     * @param key
     * @return
     */
    public static Trim trim(Cmd key) {
        return new Trim(key);
    }

    /**
     * 删除左边空格
     *
     * @param key
     * @return
     */
    public static Ltrim ltrim(Cmd key) {
        return new Ltrim(key);
    }

    /**
     * 删除右边空格
     *
     * @param key
     * @return
     */
    public static Rtrim rtrim(Cmd key) {
        return new Rtrim(key);
    }

    /**
     * 字符串比较 函数
     * 返回 -1 0 1
     *
     * @param key
     * @param str
     * @return
     */
    public static Strcmp strcmp(Cmd key, String str) {
        return new Strcmp(key, str);
    }

    /**
     * 将字符串  重复 n 次
     *
     * @param key
     * @param n
     * @return
     */
    public static Repeat repeat(Cmd key, int n) {
        return new Repeat(key, n);
    }


    /**
     * 替换 函数
     *
     * @param key
     * @param target      匹配目标
     * @param replacement 替换值
     * @return
     */
    public static Replace replace(Cmd key, String target, String replacement) {
        return new Replace(key, target, replacement);
    }

    /**
     * 反转函数
     *
     * @param key
     * @return
     */
    public static Reverse reverse(Cmd key) {
        return new Reverse(key);
    }

    /**
     * 匹配 match 在 key里边的位置
     * key 需要符合逗号分割规范
     *
     * @param key
     * @param match
     * @return
     */
    public static FindInSet findInSet(Cmd key, String match) {
        return new FindInSet(key, match);
    }

    /**
     * 匹配key 在values里的位置 从1 开始
     *
     * @param key
     * @param values 数据
     * @return
     */
    public static Filed filed(Cmd key, Serializable... values) {
        return new Filed(key, values);
    }

    /**
     * 当前日期
     *
     * @return
     */
    public static CurrentDate currentDate() {
        return new CurrentDate();
    }

    /**
     * 当前时间（不包含日期）
     *
     * @return
     */
    public static CurrentTime currentTime() {
        return new CurrentTime();
    }

    /**
     * 当前时间（包含日期）
     *
     * @return
     */
    public static CurrentDateTime currentDateTime() {
        return new CurrentDateTime();
    }

    /**
     * 以 UNIX 时间戳的形式返回当前时间(单位秒)
     *
     * @return
     */
    public static UnixTimestamp unixTimestamp() {
        return new UnixTimestamp();
    }

    /**
     * 指定时间key时间 以 UNIX 时间戳的形式返回(单位秒)
     *
     * @return
     */
    public static UnixTimestamp unixTimestamp(Cmd key) {
        return new UnixTimestamp(key);
    }

    /**
     * 将UNIX时间戳转成时间
     *
     * @param key
     * @return
     */
    public static FromUnixTime fromUnixTime(Cmd key) {
        return new FromUnixTime(key);
    }

    /**
     * 获取年份
     *
     * @param key
     * @return
     */
    public static Year year(Cmd key) {
        return new Year(key);
    }

    /**
     * 获取月份
     *
     * @param key
     * @return
     */
    public static Month month(Cmd key) {
        return new Month(key);
    }

    /**
     * 获取日期部分，不包含时分秒
     *
     * @param key
     * @return
     */
    public static Date date(Cmd key) {
        return new Date(key);
    }

    /**
     * 获取第几天
     *
     * @param key
     * @return
     */
    public static Day day(Cmd key) {
        return new Day(key);
    }

    /**
     * 获取星期几
     *
     * @param key
     * @return
     */
    public static Weekday weekday(Cmd key) {
        return new Weekday(key);
    }


    /**
     * 获取小时
     *
     * @param key
     * @return
     */
    public static Hour hour(Cmd key) {
        return new Hour(key);
    }

    /**
     * 日期比较
     *
     * @param key
     * @return
     */
    public static DateDiff dateDiff(Cmd key, Cmd another) {
        return new DateDiff(key, another);
    }

    /**
     * 日期增加
     *
     * @param key
     * @return
     */
    public static DateAdd dateAdd(Cmd key, int n, TimeUnit timeUnit) {
        return new DateAdd(key, n, timeUnit);
    }

    /**
     * 日期减少
     *
     * @param key
     * @return
     */
    public static DateSub dateSub(Cmd key, int n, TimeUnit timeUnit) {
        return new DateSub(key, n, timeUnit);
    }

    /**
     * md5
     *
     * @param str
     * @return
     */
    public static Md5 md5(String str) {
        return new Md5(str);
    }

    /**
     * md5
     *
     * @param key
     * @return
     */
    public static Md5 md5(Cmd key) {
        return new Md5(key);
    }

    /**
     * 将ip转成数字
     *
     * @param ip
     * @return
     */
    public static InetAton inetAton(String ip) {
        return new InetAton(ip);
    }

    /**
     * 将ip转成数字
     *
     * @param key
     * @return
     */
    public static InetAton inetAton(Cmd key) {
        return new InetAton(key);
    }

    /**
     * 将ip数字转成ip
     *
     * @param ipNumber
     * @return
     */
    public static InetNtoa inetNtoa(Number ipNumber) {
        return new InetNtoa(ipNumber);
    }

    /**
     * 将ip数字转成ip
     *
     * @param key
     * @return
     */
    public static InetNtoa inetNtoa(Cmd key) {
        return new InetNtoa(key);
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
     * @param key
     * @param value
     * @return
     */
    public static IfNull ifNull(Cmd key, Cmd value) {
        return new IfNull(key, value);
    }

    /**
     * IFNULL(条件,值1,值2) 函数
     *
     * @param key
     * @param value
     * @return
     */
    public static IfNull ifNull(Cmd key, Serializable value) {
        return new IfNull(key, value);
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
    public static In in(Cmd key, IQuery query) {
        return new In(key).add(query);
    }

    /**
     * exists 一个查询
     *
     * @param query
     * @return
     */
    public static Exists exists(IQuery query) {
        return new Exists(query);
    }

    /**
     * not exists 一个查询
     *
     * @param query
     * @return
     */
    public static NotExists notExists(IQuery query) {
        return new NotExists(query);
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
