package cn.mybatis.mp.db.annotations;

import java.lang.annotation.*;

/**
 * 逻辑删除
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LogicDelete {

    /**
     * 删除前的值
     * 无法设置动态值，空时表示 初始值为NULL
     *
     * @return 初始值
     */
    String beforeValue() default "";

    /**
     * 删除后的值
     * 可设置动态值，动态值格式为 {key} 例如：当前时间-{NOW}
     *
     * @return 删除后的值
     */
    String afterValue();

    /**
     * 删除时间字段
     * 可不设置
     * 支持 Date LocalDateTime
     * 支持 Int(秒)，Long(毫秒)
     *
     * @return 删除时间字段
     */
    String deleteTimeField() default "";

}
