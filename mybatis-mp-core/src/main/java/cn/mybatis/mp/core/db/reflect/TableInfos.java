package cn.mybatis.mp.core.db.reflect;

import cn.mybatis.mp.core.util.FieldUtils;
import cn.mybatis.mp.core.util.StringPool;
import cn.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import cn.mybatis.mp.core.util.NamingUtil;
import cn.mybatis.mp.db.annotations.Table;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * table 信息库
 */
public final class TableInfos {

    private static final Map<Class, TableInfo> TABLE_INFO_MAP = new ConcurrentHashMap<>();

    private TableInfos() {

    }

    private static TableBasicInfo buildTableBasic(Class tableClass, Table table, MybatisConfiguration mybatisConfiguration) {
        String tableName = table.value();
        if (StringPool.EMPTY.equals(tableName)) {
            //未设置表名
            tableName = tableClass.getSimpleName();
            if (mybatisConfiguration.isTableUnderline()) {
                tableName = NamingUtil.camelToUnderline(tableName);
            }
        }
        return new TableBasicInfo(tableClass, table.schema(), tableName);
    }

    private static TableInfo buildTableInfo(TableBasicInfo tableBasicInfo, MybatisConfiguration mybatisConfiguration) {
        return new TableInfo(tableBasicInfo, FieldUtils.getResultMappingFields(tableBasicInfo.getEntityClass()).stream().map((f) -> {
            return new FieldInfo(tableBasicInfo, f, mybatisConfiguration);
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
        if (Objects.isNull(table)) {
            return null;
        }

        TableBasicInfo tableBasicInfo = buildTableBasic(tableClass, table, mybatisConfiguration);
        tableInfo = buildTableInfo(tableBasicInfo, mybatisConfiguration);
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
        if(tableClass==null){
            return null;
        }
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
        if (Objects.isNull(entityReflect)) {
            return load(tableClass, mybatisConfiguration);
        }
        return entityReflect;
    }
}
