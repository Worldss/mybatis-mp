package cn.mybatis.mp.core.incrementer;

import cn.mybatis.mp.core.util.StringPool;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * UUID 自增器
 */
public class UUIDGenerator implements IdentifierGenerator<String> {

    @Override    public String nextId(Class<?> entity) {

        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong()).toString().replace(StringPool.DASH, StringPool.EMPTY);
    }
}
