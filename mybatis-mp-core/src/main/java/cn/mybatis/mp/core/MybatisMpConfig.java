package cn.mybatis.mp.core;


import cn.mybatis.mp.core.sql.MybatisMpQuerySQLBuilder;
import cn.mybatis.mp.core.sql.QuerySQLBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局配置
 */
public final class MybatisMpConfig {

    private static final Map<String, Object> CACHE = new ConcurrentHashMap<>();
    private static final String COLUMN_UNDERLINE = "columnUnderline";
    private static final String TABLE_UNDERLINE = "tableUnderline";
    private static final String DEFAULT_BATCH_SIZE = "defaultBatchSize";
    private static final String SQL_BUILDER = "SQLBuilder";

    private static final QuerySQLBuilder DEFAULT_SQL_BUILDER = new MybatisMpQuerySQLBuilder();

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
}
