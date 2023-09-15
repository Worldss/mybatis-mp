package org.mybatis.mp.core.db.reflect;

import org.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import org.mybatis.mp.core.util.FieldUtils;
import org.mybatis.mp.core.util.StringPool;
import org.mybatis.mp.core.util.NamingUtil;
import org.mybatis.mp.db.annotations.Table;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * table 信息库
 */
public final class TableInfos {

    private static final Map<Class, TableInfo> TABLE_INFO_MAP = new ConcurrentHashMap<>();

    private TableInfos() {

    }

    private static TableBasic buildTableBasic(Class tableClass, Table table, MybatisConfiguration mybatisConfiguration) {
        String tableName = table.value();
        if (StringPool.EMPTY.equals(tableName)) {
            //未设置表名
            tableName = tableClass.getSimpleName();
            if (mybatisConfiguration.isTableUnderline()) {
                tableName = NamingUtil.camelToUnderline(tableName);
            }
        }
        return new TableBasic(tableClass, table.schema(), tableName);
    }

    private static TableInfo buildTableInfo(TableBasic tableBasic, MybatisConfiguration mybatisConfiguration) {
        return new TableInfo(tableBasic, FieldUtils.getResultMappingFields(tableBasic.getType()).stream().map((f) -> {
            return new FieldInfo(tableBasic, f, mybatisConfiguration);
        }).collect(Collectors.toList()));
    }

    public static TableInfo load(Class tableClass, MybatisConfiguration mybatisConfiguration) {
        if (!tableClass.isAnnotationPresent(Table.class)) {
            return null;
        }

        TableInfo tableInfo = TABLE_INFO_MAP.get(tableClass);
        if (tableInfo != null) {
            return tableInfo;
        }

        Table table = (Table) tableClass.getAnnotation(Table.class);
        if (table == null) {
            return null;
        }

        TableBasic tableBasic = buildTableBasic(tableClass, table, mybatisConfiguration);
        tableInfo = buildTableInfo(tableBasic, mybatisConfiguration);
        TABLE_INFO_MAP.put(tableClass, tableInfo);
        return tableInfo;
    }

    /**
     * 获取表的信息
     *
     * @param tableClass
     * @return
     */
    public static TableInfo get(Class tableClass) {
        return TABLE_INFO_MAP.get(tableClass);
    }

    /**
     * 获取表的信息
     *
     * @param tableClass
     * @param mybatisConfiguration
     * @return
     */
    public static TableInfo get(Class tableClass, MybatisConfiguration mybatisConfiguration) {
        TableInfo entityReflect = get(tableClass);
        if (entityReflect == null) {
            return load(tableClass, mybatisConfiguration);
        }
        return entityReflect;
    }
}
