package cn.mybatis.mp.core.mybatis.configuration;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局配置
 */
public final class MybatisMpConfig {

    private static final Map<String, Object> CACHE = new ConcurrentHashMap<>();
    private static final String COLUMN_UNDERLINE = "columnUnderline";
    private static final String TABLE_UNDERLINE = "tableUnderline";

    private MybatisMpConfig() {

    }

    public static boolean isColumnUnderline() {
        return (boolean) CACHE.computeIfAbsent(COLUMN_UNDERLINE, key -> true);
    }

    public static void setColumnUnderline(boolean bool) {
        CACHE.computeIfAbsent(COLUMN_UNDERLINE, key -> bool);
    }

    public static boolean isTableUnderline() {
        return (boolean) CACHE.computeIfAbsent(TABLE_UNDERLINE, key -> true);
    }

    public static void setTableUnderline(boolean bool) {
        CACHE.computeIfAbsent(TABLE_UNDERLINE, key -> bool);
    }
}
