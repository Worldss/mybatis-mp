package org.mybatis.mp.db.annotations;


import java.lang.annotation.*;

/**
 * 结果映射
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ResultTable {

    /**
     * 对应的实体类
     *
     * @return
     */
    Class value();

}
