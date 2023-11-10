package cn.mybatis.mp.core.db.reflect;


import cn.mybatis.mp.db.annotations.Table;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * table 信息库
 */
public final class Tables {

    private static final Map<Class, TableInfo> CACHE = new ConcurrentHashMap<>();

    private Tables() {

    }


    /**
     * 获取表的信息
     *
     * @param entity
     * @return
     */
    
    public static TableInfo get(Class entity) {
        if (!entity.isAnnotationPresent(Table.class)) {
            return null;
        }
        return CACHE.computeIfAbsent(entity, key -> new TableInfo(entity));
    }
}
