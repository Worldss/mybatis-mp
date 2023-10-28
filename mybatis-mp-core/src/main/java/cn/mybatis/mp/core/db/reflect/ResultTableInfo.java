package cn.mybatis.mp.core.db.reflect;

import cn.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import cn.mybatis.mp.core.util.FieldUtils;
import cn.mybatis.mp.core.util.StringPool;
import cn.mybatis.mp.db.annotations.*;
import org.apache.ibatis.mapping.ResultMapping;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ResultTableInfo {

    /**
     * 实体类前缀
     */
    private final Map<Class, String> entitysPrefix;


    /**
     * 结果映射-mybatis原生
     */
    private final List<ResultMapping> resultMappings;

    private int entityNum = -1;


    public ResultTableInfo(MybatisConfiguration configuration, Class clazz) {
        ResultEntity resultEntity = (ResultEntity) clazz.getAnnotation(ResultEntity.class);
        Map<Class, String> entitysPrefix = new HashMap<>();
        //前缀占位
        createAndGetPrefix(entitysPrefix, resultEntity.value());
        List<ResultMapping> resultMappings = FieldUtils.getResultMappingFields(clazz).stream().map(field -> {
            if (field.isAnnotationPresent(ResultField.class)) {
                ResultField resultField = field.getAnnotation(ResultField.class);
                return createResultMapping(configuration, clazz, resultField, field);
            } else if (field.isAnnotationPresent(ResultEntityField.class)) {
                ResultEntityField resultEntityField = field.getAnnotation(ResultEntityField.class);
                return createResultMapping(configuration, clazz, createAndGetPrefix(entitysPrefix, resultEntityField.target()), resultEntityField, field);
            } else if (field.isAnnotationPresent(NestedResultEntity.class)) {
                NestedResultEntity nestedResultEntity = field.getAnnotation(NestedResultEntity.class);
                return createNestedResultMapping(configuration, clazz, createAndGetPrefix(entitysPrefix, nestedResultEntity.target()), nestedResultEntity, field);
            } else {
                return createResultMapping(configuration, clazz, resultEntity, field);
            }
        }).collect(Collectors.toList());


        this.resultMappings = Collections.unmodifiableList(resultMappings);
        this.entitysPrefix = Collections.unmodifiableMap(entitysPrefix);
    }

    private String createAndGetPrefix(Map<Class, String> entitysPrefix, Class entity) {
        return entitysPrefix.computeIfAbsent(entity, key -> {
            ++entityNum;
            if (entityNum == 0) {
                return StringPool.EMPTY;
            } else if (entityNum == 1) {
                return "$";
            } else {
                return "$" + entityNum;
            }
        });
    }


    /**
     * 根据 @ResultEntity 匹配并构建ResultMapping
     *
     * @param configuration
     * @param clazz
     * @param field
     * @param configuration
     * @return
     */
    private static ResultMapping createResultMapping(MybatisConfiguration configuration, Class clazz, ResultEntity resultEntity, Field field) {
        TableInfo tableInfo = TableInfos.get(configuration, resultEntity.value());
        String targetEntityName = resultEntity.value().getName();
        if (Objects.isNull(tableInfo)) {
            throw new RuntimeException(String.format("unable match field:%s in class % , the class of @ResultEntity(class): %s is not a entity", field.getName(), clazz.getName(), targetEntityName));
        }

        String targetFieldName = field.getName();
        TableFieldInfo tableFieldInfo = tableInfo.getFieldInfo(targetFieldName);
        if (Objects.isNull(tableFieldInfo)) {
            throw new RuntimeException(String.format("unable match field:%s in class % ,The field %s can't found in entity class:%s", field.getName(), clazz.getName(), targetFieldName, targetEntityName));
        }
        return tableFieldInfo.getResultMapping();
    }

    /**
     * 根据 @ResultEntityField 匹配并构建ResultMapping
     *
     * @param configuration
     * @param clazz
     * @param columnPrefix
     * @param resultEntityField
     * @param field
     * @return
     */
    private static ResultMapping createResultMapping(MybatisConfiguration configuration, Class clazz, String columnPrefix, ResultEntityField resultEntityField, Field field) {
        TableInfo tableInfo = TableInfos.get(configuration, resultEntityField.target());
        String targetEntityName = resultEntityField.target().getName();
        if (Objects.isNull(tableInfo)) {
            throw new RuntimeException(String.format("unable match field:%s in class % , the class of @ResultEntityField(target::class): %s is not a entity", field.getName(), clazz.getName(), targetEntityName));
        }
        String targetFieldName = resultEntityField.property();
        TableFieldInfo tableFieldInfo = tableInfo.getFieldInfo(targetFieldName);
        if (Objects.isNull(tableFieldInfo)) {
            throw new RuntimeException(String.format("unable match field:%s in class % ,The field %s can't found in entity class:%s", field.getName(), clazz.getName(), targetFieldName, targetEntityName));
        }
        if (Objects.isNull(columnPrefix) || StringPool.EMPTY.equals(columnPrefix)) {
            return tableFieldInfo.getResultMapping();
        } else {
            return configuration.buildResultMapping(field, columnPrefix + tableFieldInfo.getColumnName(), tableFieldInfo.getFieldAnnotation().jdbcType(), tableFieldInfo.getFieldAnnotation().typeHandler());
        }
    }

    /**
     * 根据 @ResultField 匹配并构建ResultMapping
     *
     * @param configuration
     * @param clazz
     * @param resultField
     * @param field
     * @return
     */
    private static ResultMapping createResultMapping(MybatisConfiguration configuration, Class clazz, ResultField resultField, Field field) {
        return configuration.buildResultMapping(field, resultField.value(), resultField.jdbcType(), resultField.typeHandler());
    }

    /**
     * 根据 @NestedResultEntity 匹配并构建ResultMapping
     *
     * @param configuration
     * @param clazz
     * @param columnPrefix
     * @param nestedResultEntity
     * @param field
     * @return
     */
    private static ResultMapping createNestedResultMapping(MybatisConfiguration configuration, Class clazz, String columnPrefix, NestedResultEntity nestedResultEntity, Field field) {
        TableInfo tableInfo = TableInfos.get(configuration, nestedResultEntity.target());
        String targetEntityName = nestedResultEntity.target().getName();
        if (Objects.isNull(tableInfo)) {
            throw new RuntimeException(String.format("unable match field:%s in class % , the class of @NestedResultEntity(target::class): %s is not a entity", field.getName(), clazz.getName(), targetEntityName));
        }
        // 内嵌ID
        String nestedResultMapId = clazz.getName() + "." + field.getName();

        //查看是否已注册了内嵌ResultMap
        if (!configuration.hasResultMap(nestedResultMapId)) {
            //创建并注册内嵌ResultMap
            List<ResultMapping> nestedMappings = FieldUtils.getResultMappingFields(field.getType()).stream().map(nestedFiled -> {
                String targetFieldName = nestedFiled.getName();
                NestedResultEntityField nestedResultEntityField = nestedFiled.getAnnotation(NestedResultEntityField.class);
                if (Objects.nonNull(nestedResultEntityField)) {
                    targetFieldName = nestedResultEntityField.property();
                } else if (nestedFiled.isAnnotationPresent(ResultField.class)) {
                    ResultField resultField = field.getAnnotation(ResultField.class);
                    return configuration.buildResultMapping(nestedFiled, resultField.value(), resultField.jdbcType(), resultField.typeHandler());
                }
                TableFieldInfo tableFieldInfo = tableInfo.getFieldInfo(targetFieldName);
                if (Objects.isNull(tableFieldInfo)) {
                    throw new RuntimeException(String.format("unable match field:%s in class:s% ,The nested field:%s can't found in entity class:%s", field.getName(), clazz.getName(), targetFieldName, targetEntityName));
                }
                if (tableFieldInfo.getField().getType() != nestedFiled.getType()) {
                    throw new RuntimeException(MessageFormat.format("unable match field {0} in class {1} ,The type of nested field {2}.{3} must be {4}", field.getName(), clazz.getName(), targetEntityName, targetFieldName, tableFieldInfo.getField().getType().getName()));
                }
                return configuration.buildResultMapping(nestedFiled, columnPrefix + tableFieldInfo.getColumnName(), tableFieldInfo.getFieldAnnotation().jdbcType(), tableFieldInfo.getFieldAnnotation().typeHandler());
            }).collect(Collectors.toList());
            configuration.registerNestedResultMap(nestedResultMapId, field, nestedMappings);
        }
        return configuration.buildNestedResultMapping(nestedResultMapId, field);
    }

    public List<ResultMapping> getResultMappings() {
        return resultMappings;
    }

    public Map<Class, String> getEntitysPrefix() {
        return entitysPrefix;
    }
}
