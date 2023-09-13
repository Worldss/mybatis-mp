package org.mybatis.mp.core.mybatis.configuration;

/**
 * Mybatis mp 全局配置
 */
public class MybatisMpConfig {

    /**
     * 列名否是下划线命名
     */
    private boolean columnUnderline = true;


    /**
     * 字段名否是下划线命名
     */
    private boolean fieldUnderline = false;

    /**
     * 表名否是下划线命名
     */
    private boolean tableUnderline = true;

    public boolean isColumnUnderline() {
        return columnUnderline;
    }

    public void setColumnUnderline(boolean columnUnderline) {
        this.columnUnderline = columnUnderline;
    }

    public boolean isTableUnderline() {
        return tableUnderline;
    }

    public void setTableUnderline(boolean tableUnderline) {
        this.tableUnderline = tableUnderline;
    }

    public boolean isFieldUnderline() {
        return fieldUnderline;
    }

    public void setFieldUnderline(boolean fieldUnderline) {
        this.fieldUnderline = fieldUnderline;
    }
}
