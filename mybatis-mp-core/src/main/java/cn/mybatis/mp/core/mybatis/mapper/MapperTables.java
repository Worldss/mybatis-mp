package cn.mybatis.mp.core.mybatis.mapper;

import cn.mybatis.mp.core.util.GenericUtil;
import cn.mybatis.mp.db.annotations.Table;

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
        return GenericUtil.getGenericInterfaceClass(mapper).stream().filter(item -> {
            boolean isTableClass = item.isAnnotationPresent(Table.class);
            CACHE.put(mapper.getName(), item);
            return isTableClass;
        }).findFirst().isPresent();
    }

    public final static Class get(String mapper) {
        return CACHE.get(mapper);
    }

    public final static Class get(Class mapper) {
        return get(mapper.getName());
    }
}
