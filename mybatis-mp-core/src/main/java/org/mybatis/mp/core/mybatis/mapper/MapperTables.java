package org.mybatis.mp.core.mybatis.mapper;

import org.mybatis.mp.db.annotations.Table;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 此处的mapper 均为真实类
 */
public class MapperTables {

    private MapperTables() {
    }

    private final static Map<String, Class> CACHE = new HashMap<>();

    public final static boolean add(Class mapper) {
        Type[] genericInterfaces = mapper.getGenericInterfaces();
        boolean isEntity = false;
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
                if (actualTypeArgument instanceof Class) {
                    Class entity = (Class) actualTypeArgument;
                    if (entity.isAnnotationPresent(Table.class)) {
                        CACHE.put(mapper.getName(), entity);
                        isEntity = true;
                        break;
                    }
                }
            }
        }
        return isEntity;
    }

    public final static Class get(String mapper) {
        return CACHE.get(mapper);
    }

    public final static Class get(Class mapper) {
        return get(mapper.getName());
    }
}
