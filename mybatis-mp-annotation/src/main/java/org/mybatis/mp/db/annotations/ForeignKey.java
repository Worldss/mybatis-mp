package org.mybatis.mp.db.annotations;


import java.lang.annotation.*;

/**
 * 标记外键关系
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ForeignKey {

    Class value();
}
