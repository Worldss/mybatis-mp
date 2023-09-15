package org.mybatis.mp.core.util;

import org.mybatis.mp.db.annotations.IgnoreMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public final class FieldUtils {

    public static boolean isResultMappingField(Field field) {
        if (Modifier.isStatic(field.getModifiers())) {
            return false;
        }
        if (Modifier.isTransient(field.getModifiers())) {
            return false;
        }

        if (Modifier.isFinal(field.getModifiers())) {
            return false;
        }

        if (field.isAnnotationPresent(IgnoreMapping.class)) {
            return false;
        }
        return true;
    }

    public static List<Field> getResultMappingFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>(50);
        Class parseClass = clazz;
        while (true) {
            if (parseClass == null) {
                break;
            }
            Field[] fields = parseClass.getDeclaredFields();
            for (Field field : fields) {
                if (isResultMappingField(field)) {
                    fieldList.add(field);
                }
            }
            parseClass = parseClass.getSuperclass();
        }
        return fieldList;
    }

}
