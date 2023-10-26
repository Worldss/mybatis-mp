package cn.mybatis.mp.db.annotations;


import java.lang.annotation.*;

/**
 * 结果映射
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(ResultTable.List.class)
public @interface ResultTable {

    /**
     * 对应的实体类
     *
     * @return
     */
    Class value();

    /**
     * 属性前缀 长度越长 优先匹配
     *
     * @return
     */
    String prefix() default "";

    /**
     * 列前缀
     *
     * @return
     */
    String columnPrefix() default "";

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    @interface List {
        ResultTable[] value();
    }
}
