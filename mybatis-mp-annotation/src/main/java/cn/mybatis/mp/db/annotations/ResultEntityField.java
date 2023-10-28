package cn.mybatis.mp.db.annotations;

import java.lang.annotation.*;

/**
 * 结果字段 用于解决字段冲突问题
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ResultEntityField {

    /**
     * 对应的实体类
     *
     * @return
     */
    Class target();

    /**
     * 对应target的属性
     *
     * @return
     */
    String property();
}
