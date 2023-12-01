package cn.mybatis.mp.core;


import cn.mybatis.mp.core.sql.MybatisMpQuerySQLBuilder;
import cn.mybatis.mp.core.sql.QuerySQLBuilder;
import cn.mybatis.mp.core.util.DefaultValueConvertUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * 全局配置
 */
public final class MybatisMpConfig {

    private static final Map<String, Object> CACHE = new ConcurrentHashMap<>();
    private static final String COLUMN_UNDERLINE = "columnUnderline";
    private static final String TABLE_UNDERLINE = "tableUnderline";
    private static final String DEFAULT_BATCH_SIZE = "defaultBatchSize";
    private static final String SQL_BUILDER = "SQLBuilder";
    private static final String LOGIC_DELETE_SWITCH = "logicDeleteSwitch";
    private static final String DEFAULT_VALUE_MANAGER = "defaultValueManager";
    private static final QuerySQLBuilder DEFAULT_SQL_BUILDER = new MybatisMpQuerySQLBuilder();

    static {
        Map<String, Supplier<Object>> defaultValueMap = new ConcurrentHashMap<>();
        defaultValueMap.put("{NOW}", () -> {
            return LocalDateTime.now();
        });

        defaultValueMap.put("{DATE}", () -> {
            return LocalDate.now();
        });

        defaultValueMap.put("{DATE_NOW}", () -> {
            return new Date();
        });

        defaultValueMap.put("{NOW_TIMESTAMP}", () -> {
            return System.currentTimeMillis() / 1000;
        });

        defaultValueMap.put("{NOW_MILLISECOND}", () -> {
            return System.currentTimeMillis() / 1000;
        });

        CACHE.put(DEFAULT_VALUE_MANAGER, defaultValueMap);
    }

    private MybatisMpConfig() {

    }

    /**
     * 数据库列是否下划线规则 默认 true
     *
     * @return
     */
    public static boolean isColumnUnderline() {
        return (boolean) CACHE.computeIfAbsent(COLUMN_UNDERLINE, key -> true);
    }

    /**
     * 数据库列是否下划线规则（必须在项目启动时设置，否则可能永远不会成功）
     *
     * @param bool
     */
    public static void setColumnUnderline(boolean bool) {
        CACHE.computeIfAbsent(COLUMN_UNDERLINE, key -> bool);
    }

    /**
     * 数据库表是否下划线规则 默认 true
     *
     * @return
     */
    public static boolean isTableUnderline() {
        return (boolean) CACHE.computeIfAbsent(TABLE_UNDERLINE, key -> true);
    }

    /**
     * 设置数据库表是否下划线规则（必须在项目启动时设置，否则可能永远不会成功）
     *
     * @param bool
     */
    public static void setTableUnderline(boolean bool) {
        CACHE.computeIfAbsent(TABLE_UNDERLINE, key -> bool);
    }

    /**
     * 批量提交的默认size 默认1000
     *
     * @return
     */
    public static int getDefaultBatchSize() {
        return (int) CACHE.computeIfAbsent(DEFAULT_BATCH_SIZE, key -> 1000);
    }

    public static void setDefaultBatchSize(int defaultBatchSize) {
        if (defaultBatchSize < 1) {
            throw new RuntimeException("defaultBatchSize can't less 1");
        }
        CACHE.put(DEFAULT_BATCH_SIZE, defaultBatchSize);
    }

    /**
     * 设置QUERY SQL BUILDER
     *
     * @return
     */
    public static QuerySQLBuilder getQuerySQLBuilder() {
        return (QuerySQLBuilder) CACHE.computeIfAbsent(SQL_BUILDER, key -> DEFAULT_SQL_BUILDER);
    }

    public static void setQuerySQLBuilder(QuerySQLBuilder querySQLBuilder) {
        CACHE.put(SQL_BUILDER, querySQLBuilder);
    }

    /**
     * 获取逻辑删除开关，默认开启
     *
     * @return
     */
    public static boolean isLogicDeleteSwitchOpen() {
        return (boolean) CACHE.computeIfAbsent(LOGIC_DELETE_SWITCH, key -> true);
    }

    /**
     * 设置逻辑删除开关状态（必须在项目启动时设置，否则可能永远false）
     *
     * @param bool
     */
    public static void setLogicDeleteSwitch(boolean bool) {
        CACHE.computeIfAbsent(LOGIC_DELETE_SWITCH, key -> bool);
    }

    public static boolean isDefaultValueKeyFormat(String key) {
        return key.startsWith("{") && key.endsWith("}");
    }

    public static <T> void setDefaultValue(String key, Supplier<T> supplier) {
        checkDefaultValueKey(key);
        ((ConcurrentHashMap) CACHE.get(DEFAULT_VALUE_MANAGER)).computeIfAbsent(key, mapKey -> supplier);
    }

    private static void checkDefaultValueKey(String key) {
        if (!isDefaultValueKeyFormat(key)) {
            throw new RuntimeException("key must start with '{' and end with '}'");
        }
    }

    /**
     * 获取默认值
     *
     * @param clazz
     * @param key   默认值的key，key必须以{}包裹，例如:{NOW}
     * @param <T>
     * @return
     */
    public static <T> T getDefaultValue(Class<T> clazz, String key) {
        checkDefaultValueKey(key);
        Map<String, Supplier<T>> map = (Map<String, Supplier<T>>) CACHE.get(DEFAULT_VALUE_MANAGER);
        Supplier<T> supplier = map.get(key);
        if (supplier == null) {
            throw new RuntimeException(String.format("key: %s not set Supplier fun", key));
        }
        T value = supplier.get();
        return DefaultValueConvertUtil.convert(value, clazz);
    }
}
