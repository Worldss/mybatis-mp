package cn.mybatis.mp.db.annotations;


import java.lang.annotation.*;

/**
 * 实体类映射，会自动关键 实体类于注解类的关系
 * 无法自动映射的 可使用 @EntityField 注解（精准）
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ResultEntity {

    /**
     * 对应的实体类
     *
     * @return
     */
    Class value();
}
