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
     *
     * @return
     */
    String beforeValue();

    /**
     * 删除后的值
     *
     * @return
     */
    String afterValue();
}
