package cn.mybatis.mp.db.annotations;

import java.lang.annotation.*;

/**
 * 内嵌 精准匹配  （ 会继承 注解：NestedResultEntity 的信息），用于解决命名不一致问题
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NestedResultEntityField {

    /**
     * 对应内嵌类target的属性
     *
     * @return
     */
    String property();

}
