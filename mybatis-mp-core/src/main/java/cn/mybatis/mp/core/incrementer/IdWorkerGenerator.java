package cn.mybatis.mp.core.incrementer;

/**
 * 基于IdWorker 自增器
 */
public class IdWorkerGenerator implements IdentifierGenerator<Long> {

    @Override    public Long nextId(Class<?> entity) {
        return IdWorker.INSTANCE.nextId();
    }
}
