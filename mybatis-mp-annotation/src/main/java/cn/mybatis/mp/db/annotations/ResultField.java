package cn.mybatis.mp.db.annotations;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.lang.annotation.*;

/**
 * 用户返回非表真实列，例如 max，min后产生的列或者 新的列
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ResultField {

    /**
     * 列名
     *
     * @return
     */
    String value() default "";

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
