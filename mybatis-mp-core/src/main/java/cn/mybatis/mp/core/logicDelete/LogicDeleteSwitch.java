package cn.mybatis.mp.core.logicDelete;

/**
 * 逻辑删除开关，只在查询时起作用
 */
public final class LogicDeleteSwitch implements AutoCloseable {

    private final static ThreadLocal<Boolean> THREAD_LOCAL = new ThreadLocal<>();

    private LogicDeleteSwitch() {

    }

    public static Boolean getState() {
        return THREAD_LOCAL.get();
    }

    public static LogicDeleteSwitch with(boolean state) {
        LogicDeleteSwitch logicDeleteSwitch = new LogicDeleteSwitch();
        THREAD_LOCAL.set(state);
        return logicDeleteSwitch;
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }

    @Override
    public void close() {
        THREAD_LOCAL.remove();
    }
}
