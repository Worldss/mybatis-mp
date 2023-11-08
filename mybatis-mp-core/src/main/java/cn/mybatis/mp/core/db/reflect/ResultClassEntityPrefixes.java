package cn.mybatis.mp.core.db.reflect;

import cn.mybatis.mp.core.util.FieldUtils;
import cn.mybatis.mp.core.util.StringPool;
import cn.mybatis.mp.db.annotations.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 返回类内的实体类前缀
 */
public class ResultClassEntityPrefixes {

    private static final Map<Class, Map<Class, String>> CACHE = new ConcurrentHashMap<>();

    private ResultClassEntityPrefixes() {

    }

    @SuppressWarnings("unchecked")
    public static Map<Class, String> getEntityPrefix(Class clazz) {
        if (!clazz.isAnnotationPresent(ResultEntity.class)) {
            return null;
        }
        return CACHE.computeIfAbsent(clazz, key -> {
            Map<Class, String> entityPrefixMap = new HashMap<>();
            ResultEntity resultEntity = (ResultEntity) clazz.getAnnotation(ResultEntity.class);
            entityPrefixMap.put(resultEntity.value(), StringPool.EMPTY);

            int index = 0;
            List<Field> fieldList = FieldUtils.getResultMappingFields(clazz);
            for (Field field : fieldList) {
                if (field.isAnnotationPresent(Ignore.class)) {
                    continue;
                }
                if (field.isAnnotationPresent(ResultField.class)) {
                    continue;
                }

                Class entity = null;
                if (field.isAnnotationPresent(ResultEntityField.class)) {
                    ResultEntityField resultEntityField = field.getAnnotation(ResultEntityField.class);
                    entity = resultEntityField.target();
                } else if (field.isAnnotationPresent(NestedResultEntity.class)) {
                    NestedResultEntity nestedResultEntity = field.getAnnotation(NestedResultEntity.class);
                    entity = nestedResultEntity.target();
                }

                if (Objects.nonNull(entity)) {
                    if (entityPrefixMap.containsKey(entity)) {
                        continue;
                    }
                    index++;
                    if (index == 1) {
                        entityPrefixMap.put(entity, "_");
                    } else {
                        entityPrefixMap.put(entity, "_" + index);
                    }
                }
            }

            return Collections.unmodifiableMap(entityPrefixMap);
        });
    }

}
