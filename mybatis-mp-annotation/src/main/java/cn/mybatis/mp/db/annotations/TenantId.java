package cn.mybatis.mp.db.annotations;

import java.lang.annotation.*;

/**
 * 多租户
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TenantId {

}
