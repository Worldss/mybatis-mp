package org.mybatis.mp.core.db.reflect;

import org.mybatis.mp.core.util.StringPool;

public class TableBasicInfo {

    /**
     * 对应的实体类
     */
    private final Class entityClass;

    /**
     * 数据库 schema
     */
    private final String schema;

    /**
     * 表名
     */
    private final String tableName;

    private final String schemaAndTableName;

    public TableBasicInfo(Class entityClass, String tableName) {
        this(entityClass, "", tableName);
    }

    public TableBasicInfo(Class entityClass, String schema, String tableName) {
        this.entityClass = entityClass;
        this.tableName = tableName;
        this.schema = schema;
        if (schema == null || StringPool.EMPTY.equals(schema)) {
            this.schemaAndTableName = tableName;
        } else {
            this.schemaAndTableName = schema + "." + tableName;
        }
    }

    public Class getEntityClass() {
        return entityClass;
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
}
