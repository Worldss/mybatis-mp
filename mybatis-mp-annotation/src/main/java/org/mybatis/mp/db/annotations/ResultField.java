package org.mybatis.mp.db.annotations;

import java.lang.annotation.*;

/**
 * 结果字段
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ResultField {

    /**
     * 对应的实体类
     *
     * @return
     */
    Class target() default Void.class;

    /**
     * @return
     */
    String property() default "";

    /**
     * 前缀
     * 未指定 target 时生效
     *
     * @return
     */
    String prefix() default "";


}
