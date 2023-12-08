package cn.mybatis.mp.core.db.reflect;

import cn.mybatis.mp.core.MybatisMpConfig;
import cn.mybatis.mp.core.logicDelete.LogicDeleteUtil;
import cn.mybatis.mp.core.util.FieldUtils;
import cn.mybatis.mp.core.util.StringPool;
import cn.mybatis.mp.core.util.TableInfoUtil;
import cn.mybatis.mp.db.annotations.ForeignKey;
import cn.mybatis.mp.db.annotations.LogicDelete;
import cn.mybatis.mp.db.annotations.Table;

import java.lang.reflect.Field;
import java.util.*;

public class TableInfo {

    /**
     * 对应的类
     */
    private final Class type;

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
     * 字段个数
     */
    private final int fieldSize;

    /**
     * id字段信息
     */
    private final TableFieldInfo idFieldInfo;

    /**
     * 乐观锁字段
     */
    private final TableFieldInfo versionFieldInfo;

    /**
     * 多租户字段
     */
    private final TableFieldInfo tenantIdFieldInfo;

    /**
     * 逻辑删除字段
     */
    private final TableFieldInfo logicDeleteFieldInfo;

    /**
     * 外键关系
     */
    private final Map<Class, ForeignInfo> foreignInfoMap;

    /**
     * 字段信息 key为属性字段名 value为字段信息
     */
    private final Map<String, TableFieldInfo> tableFieldInfoMap;

    public TableInfo(Class entity) {
        this.type = entity;

        Table table = (Table) entity.getAnnotation(Table.class);
        this.schema = table.schema();
        this.tableName = TableInfoUtil.getTableName(entity);
        if (schema == null || StringPool.EMPTY.equals(schema)) {
            this.schemaAndTableName = tableName;
        } else {
            this.schemaAndTableName = schema + "." + tableName;
        }

        TableFieldInfo idFieldInfo = null;
        TableFieldInfo versionFieldInfo = null;
        TableFieldInfo tenantIdFieldInfo = null;
        TableFieldInfo logicDeleteFieldInfo = null;

        List<TableFieldInfo> tableFieldInfos = new ArrayList<>();
        Map<String, TableFieldInfo> tableFieldInfoMap = new HashMap<>();
        Map<Class, ForeignInfo> foreignInfoMap = new HashMap<>();

        List<Field> fieldList = FieldUtils.getResultMappingFields(entity);
        for (Field field : fieldList) {
            TableFieldInfo tableFieldInfo = new TableFieldInfo(field);
            tableFieldInfos.add(tableFieldInfo);
            tableFieldInfoMap.put(field.getName(), tableFieldInfo);

            if (field.isAnnotationPresent(ForeignKey.class)) {
                ForeignKey foreignKey = field.getAnnotation(ForeignKey.class);
                foreignInfoMap.put(foreignKey.value(), new ForeignInfo(foreignKey.value(), tableFieldInfo));
            }
            if (idFieldInfo == null && tableFieldInfo.isTableId()) {
                idFieldInfo = tableFieldInfo;
            }
            if (tableFieldInfo.isVersion()) {
                if (versionFieldInfo != null) {
                    throw new RuntimeException(String.format("Entity %s has multi @Version", entity.getName()));
                }
                versionFieldInfo = tableFieldInfo;
            }
            if (tableFieldInfo.isTenantId()) {
                if (tenantIdFieldInfo != null) {
                    throw new RuntimeException(String.format("Entity %s has multi @TenantId", entity.getName()));
                }
                tenantIdFieldInfo = tableFieldInfo;
            }
            if (tableFieldInfo.isLogicDelete()) {
                if (logicDeleteFieldInfo != null) {
                    throw new RuntimeException(String.format("Entity %s has multi @LogicDelete", entity.getName()));
                }
                logicDeleteFieldInfo = tableFieldInfo;
                LogicDelete logicDeleteAnnotation = field.getAnnotation(LogicDelete.class);
                if (MybatisMpConfig.isDefaultValueKeyFormat(logicDeleteAnnotation.beforeValue())) {
                    throw new RuntimeException(String.format("the @LogicDelete of Entity %s has config error,the beforeValue can't be dynamic key", entity.getName()));
                }
            }
        }

        this.tableFieldInfos = Collections.unmodifiableList(tableFieldInfos);
        this.fieldSize = this.tableFieldInfos.size();
        this.idFieldInfo = idFieldInfo;
        this.versionFieldInfo = versionFieldInfo;
        this.tenantIdFieldInfo = tenantIdFieldInfo;
        this.logicDeleteFieldInfo = logicDeleteFieldInfo;

        this.tableFieldInfoMap = Collections.unmodifiableMap(tableFieldInfoMap);
        this.foreignInfoMap = Collections.unmodifiableMap(foreignInfoMap);

        if (Objects.nonNull(this.logicDeleteFieldInfo)) {
            String deleteTimeFieldName = this.logicDeleteFieldInfo.getLogicDeleteAnnotation().deleteTimeField();
            if (!StringPool.EMPTY.equals(deleteTimeFieldName)) {
                LogicDeleteUtil.getLogicDeleteTimeValue(this);
            }
        }
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

    public Class getType() {
        return this.type;
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

    public int getFieldSize() {
        return fieldSize;
    }

    public TableFieldInfo getIdFieldInfo() {
        return idFieldInfo;
    }

    public TableFieldInfo getVersionFieldInfo() {
        return versionFieldInfo;
    }

    public TableFieldInfo getTenantIdFieldInfo() {
        return tenantIdFieldInfo;
    }

    public TableFieldInfo getLogicDeleteFieldInfo() {
        return logicDeleteFieldInfo;
    }

}
