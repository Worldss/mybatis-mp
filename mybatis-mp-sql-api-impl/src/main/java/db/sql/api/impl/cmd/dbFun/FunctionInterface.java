package db.sql.api.impl.cmd.dbFun;

import db.sql.api.Cmd;
import db.sql.api.cmd.LikeMode;
import db.sql.api.impl.cmd.Methods;
import db.sql.api.impl.cmd.condition.*;

import java.io.Serializable;

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
        return Methods.ifNull(this, Methods.basicValue(value));
    }

    default Eq eq(Object value) {
        return Methods.eq(this, Methods.basicValue(value));
    }

    default Gt gt(Object value) {
        return Methods.gt(this, Methods.basicValue(value));
    }

    default Gte gte(Object value) {
        return Methods.gte(this, Methods.basicValue(value));
    }

    default Lt lt(Object value) {
        return Methods.lt(this, Methods.basicValue(value));
    }

    default Lte lte(Object value) {
        return Methods.lte(this, Methods.basicValue(value));
    }

    default Ne ne(Object value) {
        return Methods.ne(this, Methods.basicValue(value));
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
