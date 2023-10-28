package cn.mybatis.mp.core.util;

import cn.mybatis.mp.core.db.reflect.Default;
import cn.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import cn.mybatis.mp.db.DbType;
import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.TableId;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.lang.reflect.Field;
import java.util.Objects;

public final class TableInfoUtil {

    public static String getTableName(MybatisConfiguration configuration, Class entity) {
        Table table = (Table) entity.getAnnotation(Table.class);
        String tableName = table.value();
        if (StringPool.EMPTY.equals(tableName)) {
            //未设置表
            tableName = entity.getSimpleName();
            if (configuration.isTableUnderline()) {
                tableName = NamingUtil.camelToUnderline(tableName);
            }
        }
        return tableName;
    }

    /**
     * 获取主键的注解信息，非ID 返回 null
     *
     * @param configuration
     * @param field
     * @return
     */
    public final static TableId getTableIdAnnotation(MybatisConfiguration configuration, Field field) {
        TableId[] tableIdAnnotations = field.getAnnotationsByType(TableId.class);
        if (tableIdAnnotations.length < 1) {
            return null;
        }
        TableId tableId = null;
        for (TableId item : tableIdAnnotations) {
            if (Objects.isNull(tableId) && item.dbType() == DbType.DEFAULT) {
                tableId = item;
            }
            if (item.dbType().name().equals(configuration.getDatabaseId())) {
                tableId = item;
                break;
            }
        }
        if (Objects.isNull(tableId)) {
            tableId = tableIdAnnotations[0];
        }
        return tableId;
    }

    /**
     * 获取TableField注解信息 未配置则用默认的 Default.defaultTableFieldAnnotation()
     *
     * @param field
     * @return
     */
    public final static TableField getTableFieldAnnotation(Field field) {
        TableField tableFieldAnnotation = field.getAnnotation(TableField.class);
        if (Objects.isNull(tableFieldAnnotation)) {
            tableFieldAnnotation = Default.defaultTableFieldAnnotation();
        }
        return tableFieldAnnotation;
    }

    /**
     * 获取列名
     *
     * @param configuration
     * @param field
     * @return
     */
    public final static String getFieldColumnName(MybatisConfiguration configuration, Field field, TableField tableFieldAnnotation) {
        String columnName = tableFieldAnnotation.value();
        if (StringPool.EMPTY.equals(columnName)) {
            columnName = field.getName();
            if (configuration.isColumnUnderline()) {
                columnName = NamingUtil.camelToUnderline(columnName);
            }
        }
        return columnName;
    }


    /**
     * 获取字段结果映射
     *
     * @param configuration
     * @param field
     * @param columnName
     * @param jdbcType
     * @param typeHandler
     * @return
     */
    public final static ResultMapping getFieldResultMapping(MybatisConfiguration configuration, Field field, String columnName, JdbcType jdbcType, Class<? extends TypeHandler<?>> typeHandler) {
        return configuration.buildResultMapping(field, columnName, jdbcType, typeHandler);
    }

}
