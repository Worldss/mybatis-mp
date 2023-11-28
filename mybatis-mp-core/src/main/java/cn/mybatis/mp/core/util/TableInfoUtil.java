package cn.mybatis.mp.core.util;

import cn.mybatis.mp.core.MybatisMpConfig;
import cn.mybatis.mp.core.db.reflect.Default;
import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.TableId;
import db.sql.api.DbType;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.Objects;


public final class TableInfoUtil {

    public static String getTableName(Class entity) {
        Table table = (Table) entity.getAnnotation(Table.class);
        String tableName = table.value();
        if (StringPool.EMPTY.equals(tableName)) {
            //未设置表
            tableName = entity.getSimpleName();
            if (MybatisMpConfig.isTableUnderline()) {
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
    public static TableId getTableIdAnnotation(Configuration configuration, Field field) {
        TableId[] tableIdAnnotations = field.getAnnotationsByType(TableId.class);
        if (tableIdAnnotations.length < 1) {
            return null;
        }
        TableId tableId = null;
        for (TableId item : tableIdAnnotations) {
            if (Objects.isNull(tableId) && item.dbType() == DbType.MYSQL) {
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
    public static TableField getTableFieldAnnotation(Field field) {
        TableField tableFieldAnnotation = field.getAnnotation(TableField.class);
        if (Objects.isNull(tableFieldAnnotation)) {
            tableFieldAnnotation = Default.defaultTableFieldAnnotation();
        }
        return tableFieldAnnotation;
    }

    /**
     * 获取列名
     *
     * @param field
     * @return
     */
    public static String getFieldColumnName(Field field) {
        TableField tableFieldAnnotation = getTableFieldAnnotation(field);
        String columnName = tableFieldAnnotation.value();
        if (StringPool.EMPTY.equals(columnName)) {
            columnName = field.getName();
            if (MybatisMpConfig.isColumnUnderline()) {
                columnName = NamingUtil.camelToUnderline(columnName);
            }
        }
        return columnName;
    }
}
