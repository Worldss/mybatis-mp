package cn.mybatis.mp.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 默认值转换
 */
public final class TypeConvertUtil {

    private TypeConvertUtil() {
    }

    /**
     * 默认值转换
     *
     * @param value
     * @param targetType
     * @param <T>
     * @return
     */
    public static <T> T convert(Object value, Class<T> targetType) {
        if (value == null) {
            return null;
        }
        if (value.getClass() == targetType) {
            return (T) value;
        }
        if (value instanceof String && value.equals("")) {
            return null;
        }

        Object newValue;
        if (targetType == Boolean.class) {
            newValue = Boolean.valueOf(value.toString());
        } else if (targetType == Byte.class) {
            newValue = Byte.valueOf(value.toString());
        } else if (targetType == Integer.class) {
            newValue = Integer.valueOf(value.toString());
        } else if (targetType == Long.class) {
            newValue = Long.valueOf(value.toString());
        } else if (targetType == BigDecimal.class) {
            newValue = new BigDecimal(value.toString());
        } else if (targetType == BigInteger.class) {
            newValue = new BigInteger(value.toString());
        } else {
            throw new RuntimeException("Inconsistent types");
        }

        return (T) newValue;
    }
}
