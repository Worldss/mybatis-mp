package cn.mybatis.mp.core.incrementer;

import java.util.HashMap;
import java.util.Map;

/**
 * 自增器 工厂
 */
public class IdentifierGeneratorFactory {

    private static final Map<String, IdentifierGenerator<?>> IDENTIFIER_GENERATOR_MAP = new HashMap<>();

    static {
        IdWorkerGenerator idWorkerGenerator = new IdWorkerGenerator();
        IDENTIFIER_GENERATOR_MAP.put(IdentifierGeneratorType.DEFAULT, idWorkerGenerator);
        IDENTIFIER_GENERATOR_MAP.put(IdentifierGeneratorType.UUID, new UUIDGenerator());
        IDENTIFIER_GENERATOR_MAP.put(IdentifierGeneratorType.mpNextId, idWorkerGenerator);
    }

    private IdentifierGeneratorFactory() {
    }

    /**
     * 获取自增器
     *
     * @param name
     * @return
     */
    public static <T> IdentifierGenerator<T> getIdentifierGenerator(String name) {
        if (name == null) {
            throw new RuntimeException("IdentifierGenerator name can't be null");
        }
        IdentifierGenerator<T> identifierGenerator = (IdentifierGenerator<T>) IDENTIFIER_GENERATOR_MAP.get(name);
        if (identifierGenerator == null) {
            throw new RuntimeException(name + " IdentifierGenerator is not exists");
        }
        return identifierGenerator;
    }

    /**
     * 注册自增器
     * 除了DEFAULT 注册器可以重新注册 其他均不行
     *
     * @param name
     * @param identifierGenerator
     */
    public static void register(String name, IdentifierGenerator<?> identifierGenerator) {
        if (!IdentifierGeneratorType.DEFAULT.equals(name) && IDENTIFIER_GENERATOR_MAP.containsKey(name)) {
            throw new RuntimeException(name + " IdentifierGenerator already exists");
        }
        IDENTIFIER_GENERATOR_MAP.put(name, identifierGenerator);
    }
}
