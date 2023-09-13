package org.mybatis.mp.core.db.reflect;

import org.apache.ibatis.mapping.ResultMapping;

import java.util.List;
import java.util.stream.Collectors;

public class ResultTableInfo {

    private final Class type;

    /**
     * 所有 字段
     */
    private final List<ResultTableFieldInfo> fieldInfos;

    /**
     * 结果映射-mybatis原生
     */
    private final List<ResultMapping> resultMappings;

    public ResultTableInfo(Class type, List<ResultTableFieldInfo> fieldInfos) {
        this.type = type;
        this.fieldInfos = fieldInfos;
        resultMappings = fieldInfos.stream().map(item -> item.getResultMapping()).collect(Collectors.toList());
    }

    public Class getType() {
        return type;
    }

    public List<ResultTableFieldInfo> getFieldInfos() {
        return fieldInfos;
    }

    public List<ResultMapping> getResultMappings() {
        return resultMappings;
    }
}
