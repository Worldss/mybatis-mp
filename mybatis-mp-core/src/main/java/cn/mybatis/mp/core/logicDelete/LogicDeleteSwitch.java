package cn.mybatis.mp.core.logicDelete;

import java.util.Objects;

/**
 * 使用方式：
 * <pre>
 * try (LogicDeleteSwitch ignored = LogicDeleteSwitch.with(false)) {
 *    logicDeleteTestMapper.getById(1);
 * }
 * </pre>
 */
public final class LogicDeleteSwitch implements AutoCloseable {

    private volatile static ThreadLocal<Boolean> THREAD_LOCAL;

    private LogicDeleteSwitch() {

    }

    /**
     * 获得开关状态
     *
     * @return
     */
    public static Boolean getState() {
        if (Objects.isNull(THREAD_LOCAL)) {
            return false;
        }
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
        if (Objects.isNull(THREAD_LOCAL)) {
            THREAD_LOCAL = new ThreadLocal<>();
        }
        THREAD_LOCAL.set(state);
        return logicDeleteSwitch;
    }

    /**
     * 清理临时状态
     */
    public static void clear() {
        if (Objects.isNull(THREAD_LOCAL)) {
            THREAD_LOCAL.remove();
        }
    }

    @Override
    public void close() {
        clear();
    }
}
