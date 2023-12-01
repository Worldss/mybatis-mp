package cn.mybatis.mp.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 默认值转换
 */
public final class DefaultValueConvertUtil {

    private DefaultValueConvertUtil() {
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
        if (value instanceof Boolean) {
            newValue = Boolean.valueOf(value.toString());
        } else if (value instanceof Byte) {
            newValue = Byte.valueOf(value.toString());
        } else if (value instanceof Integer) {
            newValue = Integer.valueOf(value.toString());
        } else if (value instanceof Long) {
            newValue = Long.valueOf(value.toString());
        } else if (value instanceof BigDecimal) {
            newValue = new BigDecimal(value.toString());
        } else if (value instanceof BigInteger) {
            newValue = new BigInteger(value.toString());
        } else {
            throw new RuntimeException("Inconsistent types");
        }

        return (T) newValue;
    }
}
