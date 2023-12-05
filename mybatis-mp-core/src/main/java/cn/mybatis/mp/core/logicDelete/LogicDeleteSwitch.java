package cn.mybatis.mp.core.logicDelete;

/**
 * 逻辑删除开关，只在查询时起作用
 * 使用方式：
 * <pre>
 * try (LogicDeleteSwitch ignored = LogicDeleteSwitch.with(false)) {
 *    logicDeleteTestMapper.getById(1);
 * }
 * </pre>
 */
public final class LogicDeleteSwitch implements AutoCloseable {

    private final static ThreadLocal<Boolean> THREAD_LOCAL = new ThreadLocal<>();

    private LogicDeleteSwitch() {

    }

    /**
     * 获得开关状态
     *
     * @return
     */
    public static Boolean getState() {
        return THREAD_LOCAL.get();
    }

    /**
     * 设置开关
     *
     * @param state
     * @return
     */
    public static LogicDeleteSwitch with(boolean state) {
        LogicDeleteSwitch logicDeleteSwitch = new LogicDeleteSwitch();
        THREAD_LOCAL.set(state);
        return logicDeleteSwitch;
    }

    /**
     * 清理临时状态
     */
    public static void clear() {
        THREAD_LOCAL.remove();
    }

    @Override
    public void close() {
        clear();
    }
}
