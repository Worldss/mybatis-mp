package cn.mybatis.mp.db.annotations;


import java.lang.annotation.*;

/**
 * 乐观锁注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Version {

}
