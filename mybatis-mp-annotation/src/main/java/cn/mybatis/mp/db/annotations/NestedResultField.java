package cn.mybatis.mp.db.annotations;

import java.lang.annotation.*;

/**
 * 内嵌 精准匹配  （ 会继承 注解：NestedResultTable 的信息），用于解决命名不一致问题
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NestedResultField {

    /**
     * 对应target属性
     *
     * @return
     */
    String property();

}
