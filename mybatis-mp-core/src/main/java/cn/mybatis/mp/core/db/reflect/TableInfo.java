package cn.mybatis.mp.core.db.reflect;

import cn.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import cn.mybatis.mp.core.util.FieldUtils;
import cn.mybatis.mp.core.util.StringPool;
import cn.mybatis.mp.core.util.TableInfoUtil;
import cn.mybatis.mp.db.annotations.ForeignKey;
import cn.mybatis.mp.db.annotations.Table;
import org.apache.ibatis.mapping.ResultMapping;

import java.lang.reflect.Field;
import java.util.*;

public class TableInfo {

    /**
     * 数据库 schema
     */
    private final String schema;

    /**
     * 表名
     */
    private final String tableName;

    private final String schemaAndTableName;

    /**
     * 所有 字段
     */
    private final List<TableFieldInfo> tableFieldInfos;

    /**
     * id字段信息
     */
    private final TableFieldInfo idFieldInfo;

    /**
     * 结果映射-mybatis原生
     */
    private final List<ResultMapping> resultMappings;


    /**
     * 外键关系
     */
    private final Map<Class, ForeignInfo> foreignInfoMap;

    /**
     * 字段信息 key为属性字段名 value为字段信息
     */
    private final Map<String, TableFieldInfo> tableFieldInfoMap;

    public TableInfo(MybatisConfiguration configuration, Class entity) {
        Table table = (Table) entity.getAnnotation(Table.class);
        this.schema = table.schema();
        this.tableName = TableInfoUtil.getTableName(configuration, entity);
        if (schema == null || StringPool.EMPTY.equals(schema)) {
            this.schemaAndTableName = tableName;
        } else {
            this.schemaAndTableName = schema + "." + tableName;
        }

        TableFieldInfo idFieldInfo = null;
        List<TableFieldInfo> tableFieldInfos = new ArrayList<>();
        List<ResultMapping> resultMappings = new ArrayList<>();
        Map<String, TableFieldInfo> tableFieldInfoMap = new HashMap<>();
        Map<Class, ForeignInfo> foreignInfoMap = new HashMap<>();

        List<Field> fieldList = FieldUtils.getResultMappingFields(entity);
        for (Field field : fieldList) {
            TableFieldInfo tableFieldInfo = new TableFieldInfo(configuration, field);
            tableFieldInfos.add(tableFieldInfo);
            resultMappings.add(tableFieldInfo.getResultMapping());
            tableFieldInfoMap.put(field.getName(), tableFieldInfo);

            if (field.isAnnotationPresent(ForeignKey.class)) {
                ForeignKey foreignKey = field.getAnnotation(ForeignKey.class);
                foreignInfoMap.put(foreignKey.value(), new ForeignInfo(foreignKey.value(), tableFieldInfo));
            }
            if (idFieldInfo == null && tableFieldInfo.isTableId()) {
                idFieldInfo = tableFieldInfo;
            }
        }

        this.tableFieldInfos = Collections.unmodifiableList(tableFieldInfos);
        this.idFieldInfo = idFieldInfo;
        this.resultMappings = Collections.unmodifiableList(resultMappings);
        this.tableFieldInfoMap = Collections.unmodifiableMap(tableFieldInfoMap);
        this.foreignInfoMap = Collections.unmodifiableMap(foreignInfoMap);
    }

    /**
     * 根据字段名获取字段信息
     *
     * @param property
     * @return
     */
    public final TableFieldInfo getFieldInfo(String property) {
        return tableFieldInfoMap.get(property);
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

    public String getSchema() {
        return schema;
    }

    public String getTableName() {
        return tableName;
    }

    public String getSchemaAndTableName() {
        return schemaAndTableName;
    }

    public Map<Class, ForeignInfo> getForeignInfoMap() {
        return foreignInfoMap;
    }

    public Map<String, TableFieldInfo> getTableFieldInfoMap() {
        return tableFieldInfoMap;
    }

    public List<TableFieldInfo> getTableFieldInfos() {
        return tableFieldInfos;
    }

    public TableFieldInfo getIdFieldInfo() {
        return idFieldInfo;
    }

    public List<ResultMapping> getResultMappings() {
        return resultMappings;
    }

}
