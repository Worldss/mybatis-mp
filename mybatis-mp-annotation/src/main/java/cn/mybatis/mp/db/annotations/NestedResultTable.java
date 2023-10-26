package cn.mybatis.mp.db.annotations;


import java.lang.annotation.*;

/**
 * 结果映射
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NestedResultTable {

    /**
     * 对应的实体类
     *
     * @return
     */
    Class target();


    /**
     * 列前缀
     *
     * @return
     */
    String columnPrefix();

}
