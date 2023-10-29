package cn.mybatis.mp.core.mybatis.configuration;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局配置
 */
public final class MybatisMpConfig {

    private MybatisMpConfig() {

    }

    private static final Map<String, Object> CACHE = new ConcurrentHashMap<>();

    private static final String COLUMN_UNDERLINE = "columnUnderline";

    private static final String TABLE_UNDERLINE = "tableUnderline";

    public static final boolean isColumnUnderline() {
        return (boolean) CACHE.computeIfAbsent(COLUMN_UNDERLINE, key -> true);
    }

    public static final void setColumnUnderline(boolean bool) {
        CACHE.computeIfAbsent(COLUMN_UNDERLINE, key -> bool);
    }

    public static final boolean isTableUnderline() {
        return (boolean) CACHE.computeIfAbsent(TABLE_UNDERLINE, key -> true);
    }

    public static final void setTableUnderline(boolean bool) {
        CACHE.computeIfAbsent(TABLE_UNDERLINE, key -> bool);
    }
}
