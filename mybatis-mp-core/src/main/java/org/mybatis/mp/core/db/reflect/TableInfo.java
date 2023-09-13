package org.mybatis.mp.core.db.reflect;

import org.apache.ibatis.mapping.ResultMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TableInfo {

    /**
     * 基础信息
     */
    private final TableBasic basic;

    /**
     * 所有 字段
     */
    private final List<FieldInfo> fieldInfos;

    /**
     * id字段信息
     */
    private final FieldInfo idInfo;

    /**
     * 结果映射-mybatis原生
     */
    private final List<ResultMapping> resultMappings;

    private final Map<String, FieldInfo> fieldInfoMap = new HashMap<>();

    public TableInfo(TableBasic basic, List<FieldInfo> fieldInfos) {
        this.basic = basic;
        this.fieldInfos = fieldInfos;
        this.idInfo = fieldInfos.stream().filter(item -> item.isId()).findFirst().get();
        this.resultMappings = fieldInfos.stream().map(fieldInfo -> {
            fieldInfoMap.put(fieldInfo.getReflectField().getName(), fieldInfo);
            return fieldInfo.getResultMapping();
        }).collect(Collectors.toList());
    }

    /**
     * 根据字段名获取字段信息
     *
     * @param property
     * @return
     */
    public final FieldInfo getFieldInfo(String property) {
        return fieldInfoMap.get(property);
    }

    /**
     * @return
     */

    public TableBasic getBasic() {
        return basic;
    }

    public List<FieldInfo> getFieldInfos() {
        return fieldInfos;
    }

    public FieldInfo getIdInfo() {
        return idInfo;
    }

    public List<ResultMapping> getResultMappings() {
        return resultMappings;
    }
}
