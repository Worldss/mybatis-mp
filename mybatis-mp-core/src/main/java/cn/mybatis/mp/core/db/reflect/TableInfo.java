package cn.mybatis.mp.core.db.reflect;

import org.apache.ibatis.mapping.ResultMapping;
import cn.mybatis.mp.db.annotations.ForeignKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TableInfo {

    /**
     * 基础信息
     */
    private final TableBasicInfo basicInfo;

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

    private final Map<Class, ForeignInfo> foreignInfoMap = new HashMap<>();

    private final Map<String, FieldInfo> fieldInfoMap = new HashMap<>();

    public TableInfo(TableBasicInfo basicInfo, List<FieldInfo> fieldInfos) {
        this.basicInfo = basicInfo;
        this.fieldInfos = fieldInfos;
        this.idInfo = fieldInfos.stream().filter(item -> item.isId()).findFirst().get();
        this.resultMappings = fieldInfos.stream().map(fieldInfo -> {
            fieldInfoMap.put(fieldInfo.getReflectField().getName(), fieldInfo);

            if (fieldInfo.getReflectField().isAnnotationPresent(ForeignKey.class)) {
                ForeignKey foreignKey = fieldInfo.getReflectField().getAnnotation(ForeignKey.class);
                foreignInfoMap.put(foreignKey.value(), new ForeignInfo(foreignKey.value(), fieldInfo));
            }
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
     * 根据连接的表的类获取外键匹配信息
     *
     * @param entityClass
     * @return
     */
    public final ForeignInfo getForeignInfo(Class entityClass) {
        return this.foreignInfoMap.get(entityClass);
    }

    /**
     * @return
     */

    public TableBasicInfo getBasicInfo() {
        return basicInfo;
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
