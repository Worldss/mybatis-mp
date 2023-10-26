package cn.mybatis.mp.db.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

    /**
     * 表名
     *
     * @return
     */
    String value() default "";

    /**
     * 数据库的 schema
     *
     * @return
     */
    String schema() default "";

}
