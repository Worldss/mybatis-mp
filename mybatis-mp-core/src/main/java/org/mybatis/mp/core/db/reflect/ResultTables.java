package org.mybatis.mp.core.db.reflect;

import org.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import org.mybatis.mp.core.util.NamingUtil;
import org.mybatis.mp.core.util.StringPool;
import org.mybatis.mp.db.annotations.ResultField;
import org.mybatis.mp.db.annotations.ResultTable;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResultTables {

    private static final Map<Class, ResultTableInfo> RESULT_TABLE_MAP = new ConcurrentHashMap<>();

    private ResultTables() {

    }

    public static ResultTableInfo load(Class clazz, MybatisConfiguration mybatisConfiguration) {
        ResultTable resultTable = (ResultTable) clazz.getAnnotation(ResultTable.class);
        if (resultTable == null) {
            return null;
        }
        Class tableClass = resultTable.value();
        TableInfo tableInfo = TableInfos.get(tableClass, mybatisConfiguration);
        if (tableInfo == null) {
            throw new RuntimeException(clazz.getName() + " It's not a Table class");
        }
        return build(clazz, tableInfo, mybatisConfiguration);
    }

    private static ResultTableInfo build(Class clazz, TableInfo tableInfo, MybatisConfiguration mybatisConfiguration) {
        Class parseClass = clazz;
        List<ResultTableFieldInfo> fieldInfos = new ArrayList<>(parseClass.getFields().length);
        while (true) {
            if (parseClass == null) {
                break;
            }
            java.lang.reflect.Field[] fields = parseClass.getDeclaredFields();
            for (java.lang.reflect.Field f : fields) {
                if (Modifier.isStatic(f.getModifiers())) {
                    continue;
                }
                if (Modifier.isTransient(f.getModifiers())) {
                    continue;
                }
                if (Modifier.isFinal(f.getModifiers())) {
                    continue;
                }

                String targetFieldName = f.getName();

                ResultField resultField = f.getAnnotation(ResultField.class);

                TableInfo targetTableInfo = tableInfo;
                if (resultField != null) {
                    if (resultField.target() != Void.class) {
                        targetTableInfo = TableInfos.get(resultField.target(), mybatisConfiguration);
                        if (targetTableInfo == null) {
                            throw new RuntimeException(resultField.target().getName() + " It's not a Table class");
                        }
                    }
                    if (!StringPool.EMPTY.equals(resultField.property())) {
                        targetFieldName = resultField.property();
                    } else if (!StringPool.EMPTY.equals(resultField.prefix())) {
                        targetFieldName = f.getName().replaceFirst(resultField.prefix(), "");
                        //替换一个字母为小写
                        targetFieldName = NamingUtil.firstToLower(targetFieldName);
                    }
                }

                FieldInfo fieldInfo = targetTableInfo.getFieldInfo(targetFieldName);
                if (fieldInfo == null) {
                    throw new RuntimeException(String.format("Unable to find table attribute:%s in table:%s", targetFieldName, targetTableInfo.getBasic().getType()));
                }

                if (fieldInfo.getReflectField().getType() != f.getType()) {
                    throw new RuntimeException(String.format("This type of the attribute:%s must be %s", targetFieldName, fieldInfo.getReflectField().getType()));
                }

                fieldInfos.add(new ResultTableFieldInfo(f, fieldInfo, mybatisConfiguration));
            }

            parseClass = parseClass.getSuperclass();
        }
        return new ResultTableInfo(clazz, fieldInfos);
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
