package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.cmd.LikeMode;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.cmd.condition.*;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public interface FunctionInterface extends Cmd {

    default Sum sum() {
        return Methods.sum(this);
    }

    default Max max() {
        return Methods.max(this);
    }

    default Min min() {
        return Methods.min(this);
    }

    default Avg avg() {
        return Methods.avg(this);
    }

    default Abs abs() {
        return Methods.abs(this);
    }

    default Pow pow(int n) {
        return Methods.pow(this, n);
    }

    default Count count() {
        return Methods.count(this);
    }

    default Round round() {
        return this.round(0);
    }

    default Round round(int precision) {
        return Methods.round(this, precision);
    }

    default Ceil ceil() {
        return Methods.ceil(this);
    }

    default Floor floor() {
        return Methods.floor(this);
    }

    default Rand rand() {
        return Methods.rand(this);
    }

    default Rand rand(Number max) {
        return Methods.rand(this, max);
    }

    default Sign sign() {
        return Methods.sign(this);
    }

    default Truncate truncate() {
        return this.truncate(0);
    }

    default Truncate truncate(int precision) {
        return Methods.truncate(this, precision);
    }

    default Sqrt sqrt() {
        return Methods.sqrt(this);
    }

    default Mod mod(Number number) {
        return Methods.mod(this, number);
    }

    default Exp exp() {
        return Methods.exp(this);
    }

    default Log log() {
        return Methods.log(this);
    }

    default Log2 log2() {
        return Methods.log2(this);
    }

    default Log10 log10() {
        return Methods.log10(this);
    }

    default Degrees degrees() {
        return Methods.degrees(this);
    }

    default Radians radians() {
        return Methods.radians(this);
    }

    default Sin sin() {
        return Methods.sin(this);
    }

    default Asin asin() {
        return Methods.asin(this);
    }

    default Cos cos() {
        return Methods.cos(this);
    }

    default Acos acos() {
        return Methods.acos(this);
    }

    default Tan tan() {
        return Methods.tan(this);
    }

    default Atan atan() {
        return Methods.atan(this);
    }

    default Cot cot() {
        return Methods.cot(this);
    }

    default CharLength charLength() {
        return Methods.charLength(this);
    }

    default Length length() {
        return Methods.length(this);
    }

    default Left left(int length) {
        return Methods.left(this, length);
    }

    default Right right(int length) {
        return Methods.right(this, length);
    }

    default Upper upper() {
        return Methods.upper(this);
    }

    default Lower lower() {
        return Methods.lower(this);
    }

    default Lpad lpad(int length, String pad) {
        return Methods.lpad(this, length, pad);
    }

    default Rpad rpad(int length, String pad) {
        return Methods.rpad(this, length, pad);
    }

    default Trim trim() {
        return Methods.trim(this);
    }

    default Ltrim ltrim() {
        return Methods.ltrim(this);
    }

    default Rtrim rtrim() {
        return Methods.rtrim(this);
    }

    default Strcmp strcmp(String s) {
        return Methods.strcmp(this, s);
    }

    default Repeat repeat(int n) {
        return Methods.repeat(this, n);
    }

    default Replace replace(String target, String replacement) {
        return Methods.replace(this, target, replacement);
    }

    default Reverse reverse() {
        return Methods.reverse(this);
    }

    default FindInSet findInSet(String str) {
        return Methods.findInSet(this, str);
    }

    default UnixTimestamp unixTimestamp() {
        return Methods.unixTimestamp(this);
    }

    default FromUnixTime fromUnixTime() {
        return Methods.fromUnixTime(this);
    }

    default Year year() {
        return Methods.year(this);
    }

    default Month month() {
        return Methods.month(this);
    }

    default Day day() {
        return Methods.day(this);
    }

    default Date date() {
        return Methods.date(this);
    }

    default Weekday weekday() {
        return Methods.weekday(this);
    }

    default Hour hour() {
        return Methods.hour(this);
    }

    default DateDiff dateDiff(Cmd another) {
        return Methods.dateDiff(this, another);
    }

    default DateAdd dateAdd(int n) {
        return this.dateAdd(n, TimeUnit.DAYS);
    }

    default DateAdd dateAdd(int n, TimeUnit timeUnit) {
        return Methods.dateAdd(this, n, timeUnit);
    }

    default DateSub dateSub(int n) {
        return this.dateSub(n, TimeUnit.DAYS);
    }

    default DateSub dateSub(int n, TimeUnit timeUnit) {
        return Methods.dateSub(this, n, timeUnit);
    }

    default Md5 md5() {
        return Methods.md5(this);
    }

    default InetAton inetAton() {
        return Methods.inetAton(this);
    }

    default InetNtoa inetNtoa() {
        return Methods.inetNtoa(this);
    }

    default Multiply multiply(Number value) {
        return Methods.multiply(this, value);
    }

    default Multiply multiply(Cmd value) {
        return Methods.multiply(this, value);
    }

    default Divide divide(Number value) {
        return Methods.divide(this, value);
    }

    default Divide divide(Cmd value) {
        return Methods.divide(this, value);
    }

    default Subtract subtract(Number value) {
        return Methods.subtract(this, value);
    }

    default Subtract subtract(Cmd value) {
        return Methods.subtract(this, value);
    }

    default Plus plus(Number value) {
        return Methods.plus(this, value);
    }

    default Plus plus(Cmd value) {
        return Methods.plus(this, value);
    }

    default Concat concat(Serializable... values) {
        return Methods.concat(this, values);
    }

    default Concat concat(Cmd... values) {
        return Methods.concat(this, values);
    }

    default ConcatAs concatAs(String split, Serializable... values) {
        return Methods.concatAs(this, split, values);
    }

    default ConcatAs concatAs(String split, Cmd... values) {
        return Methods.concatAs(this, split, values);
    }

    default IfNull ifNull(Object value) {
        return Methods.ifNull(this, Methods.convert(value));
    }

    default Eq eq(Object value) {
        return Methods.eq(this, Methods.convert(value));
    }

    default Gt gt(Object value) {
        return Methods.gt(this, Methods.convert(value));
    }

    default Gte gte(Object value) {
        return Methods.gte(this, Methods.convert(value));
    }

    default Lt lt(Object value) {
        return Methods.lt(this, Methods.convert(value));
    }

    default Lte lte(Object value) {
        return Methods.lte(this, Methods.convert(value));
    }

    default Ne ne(Object value) {
        return Methods.ne(this, Methods.convert(value));
    }

    default Between between(Serializable value1, Serializable value2) {
        return Methods.between(this, value1, value2);
    }

    default Between notBetween(Serializable value1, Serializable value2) {
        return Methods.notBetween(this, value1, value2);
    }

    default Like like(String value) {
        return like(value, LikeMode.DEFAULT);
    }

    default Like like(String value, LikeMode mode) {
        return Methods.like(this, value, mode);
    }

    default NotLike notLike(String value) {
        return notLike(value, LikeMode.DEFAULT);
    }

    default NotLike notLike(String value, LikeMode mode) {
        return Methods.notLike(this, value, mode);
    }

    default In in(Serializable... values) {
        return Methods.in(this).add(values);
    }

}
