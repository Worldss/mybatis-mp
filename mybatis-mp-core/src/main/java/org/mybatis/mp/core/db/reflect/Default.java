package org.mybatis.mp.core.db.reflect;

import org.mybatis.mp.db.annotations.TableField;

/**
 * 默认 配置
 */
public final class Default {

    @TableField
    private int ___defaultField;

    public static final TableField defaultTableFieldAnnotation() {
        try {
            return Default.class.getDeclaredField("___defaultField").getAnnotation(TableField.class);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
