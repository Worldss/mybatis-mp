package cn.mybatis.mp.core.db.reflect;

import cn.mybatis.mp.db.Model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Models {

    private Models() {

    }

    private static final Map<Class, ModelInfo> CACHE = new ConcurrentHashMap<>();

    /**
     * 获取Model的信息
     *
     * @param model
     * @return
     */
    public static ModelInfo get(Class model) {
        if (!Model.class.isAssignableFrom(model)) {
            return null;
        }
        return CACHE.computeIfAbsent(model, key -> new ModelInfo(model));
    }
}
