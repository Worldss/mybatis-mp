package cn.mybatis.mp.core.mybatis.configuration;

import cn.mybatis.mp.core.db.reflect.ResultClassEntityPrefixes;
import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.util.FieldUtils;
import cn.mybatis.mp.db.annotations.*;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ResultMapUtils {

    private ResultMapUtils() {

    }

    public static ResultMap getResultMap(MybatisConfiguration configuration, Class clazz) {
        String id = clazz.getName();
        if (configuration.hasResultMap(id)) {
            return configuration.getResultMap(id);
        }
        List<ResultMapping> resultMappings = null;
        if (clazz.isAnnotationPresent(Table.class)) {
            resultMappings = getEntityResultMappings(configuration, clazz);
        } else if (clazz.isAnnotationPresent(ResultEntity.class)) {
            resultMappings = getResultEntityResultMappings(configuration, clazz);
        }
        ResultMap resultMap = null;
        if (Objects.nonNull(resultMappings)) {
            resultMap = new ResultMap.Builder(configuration, id, clazz, resultMappings, false).build();
            configuration.addResultMap(resultMap);
        }
        return resultMap;
    }

    private static List<ResultMapping> getEntityResultMappings(MybatisConfiguration configuration, Class entity) {
        TableInfo tableInfo = Tables.get(entity);
        List<ResultMapping> resultMappings = tableInfo.getTableFieldInfos().stream().map(tableFieldInfo -> {
            return configuration.buildResultMapping(tableFieldInfo.getField(), tableFieldInfo.getColumnName(), tableFieldInfo.getTableFieldAnnotation().jdbcType(), tableFieldInfo.getTableFieldAnnotation().typeHandler());
        }).collect(Collectors.toList());
        return Collections.unmodifiableList(resultMappings);
    }

    private static List<ResultMapping> getResultEntityResultMappings(MybatisConfiguration configuration, Class clazz) {
        ResultEntity resultEntity = (ResultEntity) clazz.getAnnotation(ResultEntity.class);
        Map<Class, String> entitiesPrefixMap = ResultClassEntityPrefixes.getEntityPrefix(clazz);
        List<ResultMapping> resultMappings = FieldUtils.getResultMappingFields(clazz).stream().map(field -> {
            if (field.isAnnotationPresent(ResultField.class)) {
                ResultField resultField = field.getAnnotation(ResultField.class);
                return createResultMapping(configuration, clazz, resultField, field);
            } else if (field.isAnnotationPresent(ResultEntityField.class)) {
                ResultEntityField resultEntityField = field.getAnnotation(ResultEntityField.class);
                return createResultMapping(configuration, clazz, entitiesPrefixMap.get(resultEntityField.target()), resultEntityField, field);
            } else if (field.isAnnotationPresent(NestedResultEntity.class)) {
                NestedResultEntity nestedResultEntity = field.getAnnotation(NestedResultEntity.class);
                return createNestedResultMapping(configuration, clazz, entitiesPrefixMap.get(nestedResultEntity.target()), nestedResultEntity, field);
            } else {
                return createResultMapping(configuration, clazz, resultEntity, field);
            }
        }).collect(Collectors.toList());
        return Collections.unmodifiableList(resultMappings);
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
        TableInfo tableInfo = Tables.get(resultEntity.value());
        String targetEntityName = resultEntity.value().getName();
        if (Objects.isNull(tableInfo)) {
            throw new RuntimeException(MessageFormat.format("unable match field {0} in class {1} , the class {2} of @ResultEntity(class) is not a entity", field.getName(), clazz.getName(), targetEntityName));
        }

        String targetFieldName = field.getName();
        TableFieldInfo tableFieldInfo = tableInfo.getFieldInfo(targetFieldName);
        if (Objects.isNull(tableFieldInfo)) {
            throw new RuntimeException(MessageFormat.format("unable match field {0} in class {1} ,The field {2} can't found in entity class {3}", field.getName(), clazz.getName(), targetFieldName, targetEntityName));
        }
        return configuration.buildResultMapping(field, tableFieldInfo.getColumnName(), tableFieldInfo.getTableFieldAnnotation().jdbcType(), tableFieldInfo.getTableFieldAnnotation().typeHandler());
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
        TableInfo tableInfo = Tables.get(resultEntityField.target());
        String targetEntityName = resultEntityField.target().getName();
        if (Objects.isNull(tableInfo)) {
            throw new RuntimeException(MessageFormat.format("unable match field {0} in class {1} , the class {2} of @ResultEntityField(target::class) is not a entity", field.getName(), clazz.getName(), targetEntityName));
        }
        String targetFieldName = resultEntityField.property();
        TableFieldInfo tableFieldInfo = tableInfo.getFieldInfo(targetFieldName);
        if (Objects.isNull(tableFieldInfo)) {
            throw new RuntimeException(MessageFormat.format("unable match field {0} in class {1} ,The field {2} can't found in entity class {3}", field.getName(), clazz.getName(), targetFieldName, targetEntityName));
        }
        return configuration.buildResultMapping(field, columnPrefix + tableFieldInfo.getColumnName(), tableFieldInfo.getTableFieldAnnotation().jdbcType(), tableFieldInfo.getTableFieldAnnotation().typeHandler());
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
        TableInfo tableInfo = Tables.get(nestedResultEntity.target());
        String targetEntityName = nestedResultEntity.target().getName();
        if (Objects.isNull(tableInfo)) {
            throw new RuntimeException(MessageFormat.format("unable match field {0} in class {1} , the class {2} of @NestedResultEntity(target::class) is not a entity", field.getName(), clazz.getName(), targetEntityName));
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
                    targetFieldName = nestedResultEntityField.value();
                } else if (nestedFiled.isAnnotationPresent(ResultField.class)) {
                    ResultField resultField = nestedFiled.getAnnotation(ResultField.class);
                    return configuration.buildResultMapping(nestedFiled, resultField.value(), resultField.jdbcType(), resultField.typeHandler());
                }
                TableFieldInfo tableFieldInfo = tableInfo.getFieldInfo(targetFieldName);
                if (Objects.isNull(tableFieldInfo)) {
                    throw new RuntimeException(MessageFormat.format("unable match field {0} in class {1} ,The nested field {2} can't found in entity class {3}", field.getName(), clazz.getName(), targetFieldName, targetEntityName));
                }
                return configuration.buildResultMapping(nestedFiled, columnPrefix + tableFieldInfo.getColumnName(), tableFieldInfo.getTableFieldAnnotation().jdbcType(), tableFieldInfo.getTableFieldAnnotation().typeHandler());
            }).collect(Collectors.toList());

            //注册内嵌 ResultMap
            ResultMap resultMap = new ResultMap.Builder(configuration, nestedResultMapId, field.getType(), nestedMappings, false).build();
            configuration.addResultMap(resultMap);
        }

        //构建内嵌ResultMapping
        return new ResultMapping.Builder(configuration, field.getName()).javaType(field.getType()).jdbcType(JdbcType.UNDEFINED).nestedResultMapId(nestedResultMapId).build();
    }

}
