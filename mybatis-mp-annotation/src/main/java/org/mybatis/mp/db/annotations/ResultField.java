package org.mybatis.mp.db.annotations;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.lang.annotation.*;

/**
 * 优先级 column > property > columnPrefix
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
    Class target() default Void.class;

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

    /**
     * 列名
     *
     * @return
     */
    String column() default "";

    /**
     * 配置 列的 jdbcType
     *
     * @return
     */
    JdbcType jdbcType() default JdbcType.UNDEFINED;

    /**
     * 类型处理 针对特殊 类型的列使用
     *
     * @return
     */
    Class<? extends TypeHandler<?>> typeHandler() default UnknownTypeHandler.class;

}
