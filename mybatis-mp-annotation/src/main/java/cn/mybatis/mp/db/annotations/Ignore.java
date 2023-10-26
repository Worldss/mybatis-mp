package cn.mybatis.mp.db.annotations;

import java.lang.annotation.*;

/**
 * 忽略不处理
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Ignore {


}
