package cn.mybatis.mp.db.annotations;

import cn.mybatis.mp.db.IdAutoType;
import db.sql.api.DbType;

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
     * 自增器的名字
     * 自定义生成器 需要 实现 cn.mybatis.mp.core.incrementer.IdentifierGenerator
     * 然后 注册到ID生成器工厂 cn.mybatis.mp.core.incrementer.IdentifierGeneratorFactory.register(name,ID自增器实例)
     *
     * @return
     */
    String generatorName() default "";

    /**
     * id 自增的sql语句
     *
     * @return
     */
    String sql() default "";

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    @interface List {
        TableId[] value();
    }
}
