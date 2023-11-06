package cn.mybatis.mp.db.annotations;

import cn.mybatis.mp.db.IdAutoType;
import db.sql.api.DbType;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;

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
    IdAutoType value() default IdAutoType.AUTO;

    /**
     * 数据库类型
     *
     * @return
     */
    DbType dbType() default DbType.MYSQL;

    /**
     * value = IdIncrementType.GENERATOR 时 生效
     *
     * @return
     */
    Class<? extends KeyGenerator> generator() default SelectKeyGenerator.class;

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
    @interface List {
        TableId[] value();
    }
}
