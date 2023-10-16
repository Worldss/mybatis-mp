package org.mybatis.mp.core.db.reflect;

import org.mybatis.mp.core.util.StringPool;

public class TableBasic {

    /**
     * 对应的实体类
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

    public TableBasic(Class type, String tableName) {
        this(type, "", tableName);
    }

    public TableBasic(Class type, String schema, String tableName) {
        this.type = type;
        this.tableName = tableName;
        this.schema = schema;
        if (schema == null || StringPool.EMPTY.equals(schema)) {
            this.schemaAndTableName = tableName;
        } else {
            this.schemaAndTableName = schema + "." + tableName;
        }
    }

    public Class getType() {
        return type;
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
