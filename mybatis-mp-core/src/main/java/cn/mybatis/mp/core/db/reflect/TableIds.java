package cn.mybatis.mp.core.db.reflect;

import cn.mybatis.mp.core.util.TableInfoUtil;
import cn.mybatis.mp.db.annotations.TableId;
import org.apache.ibatis.session.Configuration;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class TableIds {

    private static final Map<Class, TableId> CACHE = new ConcurrentHashMap<>();

    private TableIds() {

    }

    public static TableId get(Class entity) {
        return CACHE.get(entity);
    }

    public static TableId get(Configuration configuration, Class entity) {
        return CACHE.computeIfAbsent(entity, key -> {
            TableFieldInfo tableFieldInfo = Tables.get(entity).getIdFieldInfo();
            if (Objects.isNull(tableFieldInfo)) {
                throw new RuntimeException(String.format("class：%s table id not found", entity.getName()));
            }
            return TableInfoUtil.getTableIdAnnotation(configuration, tableFieldInfo.getField());
        });
    }

}
