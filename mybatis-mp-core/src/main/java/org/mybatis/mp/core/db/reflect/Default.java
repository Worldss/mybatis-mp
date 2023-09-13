package org.mybatis.mp.core.db.reflect;

import org.mybatis.mp.db.annotations.Field;

/**
 * 默认 配置
 */
public final class Default {

    @Field
    private int ___defaultField;

    public static final Field defaultFieldAnnotation() {
        try {
            return Default.class.getDeclaredField("___defaultField").getAnnotation(Field.class);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
