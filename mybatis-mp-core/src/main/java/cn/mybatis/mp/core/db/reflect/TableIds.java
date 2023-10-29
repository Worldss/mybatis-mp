package cn.mybatis.mp.core.db.reflect;

import cn.mybatis.mp.core.util.TableInfoUtil;
import cn.mybatis.mp.db.annotations.TableId;
import org.apache.ibatis.session.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TableIds {

    private static final Map<Class, TableId> CACHE = new ConcurrentHashMap<>();

    private TableIds() {

    }

    public final static TableId get(Class entity) {
        return CACHE.get(entity);
    }

    public final static TableId get(Configuration configuration, Class entity) {
        return CACHE.computeIfAbsent(entity, key -> {
            TableFieldInfo tableFieldInfo = TableInfos.get(entity).getIdFieldInfo();
            return TableInfoUtil.getTableIdAnnotation(configuration, tableFieldInfo.getField());
        });
    }

}
