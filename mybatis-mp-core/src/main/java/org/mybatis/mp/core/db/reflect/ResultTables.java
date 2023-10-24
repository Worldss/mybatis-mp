package org.mybatis.mp.core.db.reflect;

import org.apache.ibatis.mapping.ResultMapping;
import org.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import org.mybatis.mp.core.util.FieldUtils;
import org.mybatis.mp.core.util.NamingUtil;
import org.mybatis.mp.core.util.StringPool;
import org.mybatis.mp.db.annotations.NestedResultField;
import org.mybatis.mp.db.annotations.NestedResultTable;
import org.mybatis.mp.db.annotations.ResultField;
import org.mybatis.mp.db.annotations.ResultTable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ResultTables {

    private static final Map<Class, ResultTableInfo> RESULT_TABLE_MAP = new ConcurrentHashMap<>();

    private ResultTables() {

    }

    private static void checkResultTableSetting(ResultTable[] resultTables) {
        Set<Class> classSet = new HashSet<>(resultTables.length);
        Set<String> prefixSet = new HashSet<>(resultTables.length);
        for (ResultTable resultTable : resultTables) {
            if (classSet.contains(resultTable.value())) {
                throw new RuntimeException(resultTable.value().getName() + " has duplicate config");
            } else if (StringPool.EMPTY.equals(resultTable.prefix()) && prefixSet.contains(resultTable.prefix())) {
                throw new RuntimeException("prefix:" + resultTable.prefix() + " has duplicate config");
            }
            classSet.add(resultTable.value());
            prefixSet.add(resultTable.prefix());
        }
    }

    public static ResultTableInfo load(Class clazz, MybatisConfiguration mybatisConfiguration) {
        ResultTable[] resultTables = (ResultTable[]) clazz.getAnnotationsByType(ResultTable.class);
        if (Objects.isNull(resultTables) || resultTables.length < 1) {
            return null;
        }
        checkResultTableSetting(resultTables);
        return build(clazz, resultTables, mybatisConfiguration);
    }

    private static ResultTableInfo build(Class clazz, ResultTable[] resultTables, MybatisConfiguration mybatisConfiguration) {
        Map<Class, TableInfo> tableInfoMap = new HashMap<>(resultTables.length);

        for (ResultTable resultTable : resultTables) {
            Class tableClass = resultTable.value();
            TableInfo tableInfo = TableInfos.get(tableClass, mybatisConfiguration);
            if (Objects.isNull(tableInfo)) {
                throw new RuntimeException(tableClass.getName() + " It's not a Table class");
            }
            tableInfoMap.put(resultTable.value(), tableInfo);
        }

        List<ResultTable> sortResultTableList = Arrays.stream(resultTables).sorted((o1, o2) -> {
            return Integer.valueOf(o2.prefix().length()).compareTo(o1.prefix().length());
        }).collect(Collectors.toList());

        List<java.lang.reflect.Field> resultMappingFields = FieldUtils.getResultMappingFields(clazz);

        List<ResultTableFieldInfo> fieldInfos = resultMappingFields.stream().map(f -> {
            if (f.isAnnotationPresent(ResultField.class)) {
                ResultField resultField = f.getAnnotation(ResultField.class);
                return createFieldInfo(clazz, resultField, f, mybatisConfiguration);
            } else if (f.isAnnotationPresent(NestedResultTable.class)) {
                NestedResultTable nestedResultTable = f.getAnnotation(NestedResultTable.class);
                return createNestedFieldInfo(clazz, nestedResultTable, f, mybatisConfiguration);
            } else {
                return createFieldInfo(clazz, f, tableInfoMap, sortResultTableList, mybatisConfiguration);
            }
        }).collect(Collectors.toList());

        return new ResultTableInfo(clazz, fieldInfos);
    }

    private static ResultTableFieldInfo createNestedFieldInfo(Class clazz, NestedResultTable nestedResultTable, Field field, MybatisConfiguration mybatisConfiguration) {
        TableInfo tableInfo = TableInfos.get(nestedResultTable.target(), mybatisConfiguration);
        if (Objects.isNull(tableInfo)) {
            throw new RuntimeException(nestedResultTable.target() + " It's not a Table class");
        }
        // 内嵌ID
        String nestedResultMapId = nestedResultTable.target().getName() + "." + field.getName();

        //查看是否已注册了内嵌ResultMap
        if (!mybatisConfiguration.hasResultMap(nestedResultMapId)) {
            //创建并注册内嵌ResultMap
            List<java.lang.reflect.Field> resultMappingFields = FieldUtils.getResultMappingFields(field.getType());
            List<ResultMapping> nestedMappings = resultMappingFields.stream().map(f -> {
                NestedResultField nestedResultField = f.getAnnotation(NestedResultField.class);
                String name = f.getName();
                if (Objects.nonNull(nestedResultField)) {
                    name = nestedResultField.property();
                }
                FieldInfo fieldInfo = tableInfo.getFieldInfo(name);
                if (Objects.isNull(fieldInfo)) {
                    throw new RuntimeException(String.format("Unable to match attribute: %s.%s.%s  in table:%s", clazz.getName(), field.getName(), f.getName(), nestedResultTable.target().getName()));
                }
                if (fieldInfo.getReflectField().getType() != f.getType()) {
                    throw new RuntimeException(String.format("The type of attribute:%s.%s.%s must be %s", clazz.getName(), field.getName(), f.getName(), fieldInfo.getReflectField().getType()));
                }
                return mybatisConfiguration.buildResultMapping(f, nestedResultTable.columnPrefix() + fieldInfo.getColumnName(), fieldInfo.getFieldAnnotation().jdbcType(), fieldInfo.getFieldAnnotation().typeHandler());
            }).collect(Collectors.toList());
            mybatisConfiguration.registerNestedResultMap(nestedResultMapId, field, nestedMappings);
        }

        ResultMapping resultMapping = mybatisConfiguration.buildNestedResultMapping(nestedResultMapId, field);
        return new ResultTableFieldInfo(field, resultMapping);
    }


    private static ResultTableFieldInfo createFieldInfo(Class clazz, ResultField resultField, Field field, MybatisConfiguration mybatisConfiguration) {
        //已设置目标
        TableInfo tableInfo = TableInfos.get(resultField.target(), mybatisConfiguration);
        if (Objects.isNull(tableInfo)) {
            throw new RuntimeException(resultField.target().getName() + " It's not a Table class");
        }

        String property = resultField.property();
        if (StringPool.EMPTY.equals(property)) {
            property = field.getName();
        }

        FieldInfo fieldInfo = tableInfo.getFieldInfo(property);
        if (Objects.isNull(fieldInfo)) {
            throw new RuntimeException(String.format("Unable to match attribute: %s.%s in table:%s", clazz.getName(), field.getName(), resultField.target().getName()));
        }
        return new ResultTableFieldInfo(field, fieldInfo, resultField, mybatisConfiguration);
    }

    private static ResultTableFieldInfo createFieldInfo(Class clazz, Field field, Map<Class, TableInfo> tableInfoMap, List<ResultTable> sortResultTableList, MybatisConfiguration mybatisConfiguration) {
        FieldInfo matchFieldInfo = null;
        ResultTable matchResultTable = null;
        for (ResultTable resultTable : sortResultTableList) {
            String targetFieldName = field.getName();

            FieldInfo fieldInfo;
            if (StringPool.EMPTY.equals(resultTable.prefix())) {
                fieldInfo = tableInfoMap.get(resultTable.value()).getFieldInfo(targetFieldName);
            } else if (field.getName().startsWith(resultTable.prefix())) {
                if (!StringPool.EMPTY.equals(resultTable.prefix())) {
                    targetFieldName = field.getName().replaceFirst(resultTable.prefix(), "");
                    //替换一个字母为小写
                    targetFieldName = NamingUtil.firstToLower(targetFieldName);
                }
                fieldInfo = tableInfoMap.get(resultTable.value()).getFieldInfo(targetFieldName);
            } else {
                continue;
            }

            if (Objects.nonNull(fieldInfo)) {
                if (Objects.nonNull(matchFieldInfo)) {
                    throw new RuntimeException(String.format("The attributes %s of the %s find multiple mapping relationship", field.getName(), clazz.getName()));
                }
                matchFieldInfo = fieldInfo;
                matchResultTable = resultTable;
            }
        }

        if (Objects.isNull(matchFieldInfo)) {
            throw new RuntimeException(String.format("Unable to match attributes %s of the %s", field.getName(), clazz.getName()));
        }
        return new ResultTableFieldInfo(field, matchFieldInfo, matchResultTable.columnPrefix(), mybatisConfiguration);
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
     * @param clazz
     * @param mybatisConfiguration
     * @return
     */
    public static ResultTableInfo get(Class clazz, MybatisConfiguration mybatisConfiguration) {
        ResultTableInfo resultTableInfo = get(clazz);
        if (resultTableInfo == null) {
            return load(clazz, mybatisConfiguration);
        }
        return resultTableInfo;
    }

}
