package cn.mybatis.mp.core.db.reflect;

import cn.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import cn.mybatis.mp.db.annotations.ResultEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResultTables {

    private static final Map<Class, ResultTableInfo> RESULT_TABLE_MAP = new ConcurrentHashMap<>();


    private ResultTables() {

    }

    public static ResultTableInfo load(MybatisConfiguration configuration, Class clazz) {
        if (!clazz.isAnnotationPresent(ResultEntity.class)) {
            return null;
        }
        return new ResultTableInfo(configuration, clazz);
    }


    /**
     * 获取结果表的信息
     *
     * @param clazz
     * @return
     */
    public static ResultTableInfo get(Class clazz) {
        return RESULT_TABLE_MAP.get(clazz);
    }

    /**
     * 获取结果表的信息
     *
     * @param configuration
     * @param clazz
     * @return
     */
    public static ResultTableInfo get(MybatisConfiguration configuration, Class clazz) {
        ResultTableInfo resultTableInfo = get(clazz);
        if (resultTableInfo == null) {
            return load(configuration, clazz);
        }
        return resultTableInfo;
    }

}
