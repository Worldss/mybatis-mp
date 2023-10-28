package cn.mybatis.mp.core.db.reflect;

import cn.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import cn.mybatis.mp.db.annotations.Table;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * table 信息库
 */
public final class TableInfos {

    private static final Map<Class, TableInfo> TABLE_INFO_MAP = new ConcurrentHashMap<>();

    private TableInfos() {

    }


    public static TableInfo load(MybatisConfiguration configuration, Class entity) {
        if (!entity.isAnnotationPresent(Table.class)) {
            return null;
        }
        return TABLE_INFO_MAP.computeIfAbsent(entity, key -> new TableInfo(configuration, entity));
    }

    /**
     * 获取表的信息
     *
     * @param tableClass
     * @return
     */
    public static TableInfo get(Class tableClass) {
        if (tableClass == null) {
            return null;
        }
        return TABLE_INFO_MAP.get(tableClass);
    }

    /**
     * 获取表的信息
     *
     * @param configuration
     * @param entity
     * @return
     */
    public static TableInfo get(MybatisConfiguration configuration, Class entity) {
        TableInfo tableInfo = get(entity);
        if (Objects.isNull(tableInfo)) {
            return load(configuration, entity);
        }
        return tableInfo;
    }
}
