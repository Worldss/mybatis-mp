package cn.mybatis.mp.core.db.reflect;


import cn.mybatis.mp.db.annotations.Table;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * table 信息库
 */
public final class TableInfos {

    private static final Map<Class, TableInfo> TABLE_INFO_MAP = new ConcurrentHashMap<>();

    private TableInfos() {

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
        return TABLE_INFO_MAP.computeIfAbsent(entity, key -> new TableInfo(entity));
    }
}
