package org.mybatis.mp.db.annotations;

import java.lang.annotation.*;

/**
 * 精准匹配 独立配置（ 和 ResultTable 配置无关）
 * 结果字段 用于解决字段冲突问题
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
    Class target();

    /**
     * 对应target属性
     *
     * @return
     */
    String property() default "";


    /**
     * 列前缀
     *
     * @return
     */
    String columnPrefix() default "";

}
