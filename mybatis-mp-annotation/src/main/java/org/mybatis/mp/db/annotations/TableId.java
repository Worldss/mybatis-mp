package org.mybatis.mp.db.annotations;

import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.mybatis.mp.db.DbType;
import org.mybatis.mp.db.IdAutoType;

import java.lang.annotation.*;

/**
 * ID 自增
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(TableId.List.class)
public @interface TableId {

    /**
     * 自增类型
     *
     * @return
     */
    IdAutoType value() default IdAutoType.NONE;

    /**
     * 数据库类型
     *
     * @return
     */
    DbType dbType() default DbType.DEFAULT;

    /**
     * value = IdIncrementType.GENERATOR 时 生效
     *
     * @return
     */
    Class<? extends KeyGenerator> generator() default NoKeyGenerator.class;

    /**
     * id 自增的sql语句
     *
     * @return
     */
    String sql() default "";

    /**
     * 是否在insert or  update 在之前执行
     * 数据库自增 强制为 true
     *
     * @return
     */
    boolean executeBefore() default false;

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface List {
        TableId[] value();
    }
}
