package org.mybatis.mp.core.sql.executor;

import db.sql.core.cmd.*;
import db.sql.core.cmd.condition.Eq;
import db.sql.core.cmd.condition.Gt;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.util.Getter;
import org.mybatis.mp.core.util.LambdaUtil;



public class MybatisCmdFactory extends CmdFactory {

    public Table table(Class tableClass) {
        Table table = new Table(TableInfos.get(tableClass).getBasic().getSchemaAndTableName());
        table.setMappingClass(tableClass);
        return table;
    }

    public <T> DatasetField field(Dataset table, Getter<T> getter) {
        String fieldName = LambdaUtil.getName(getter);
        TableInfo tableInfo = TableInfos.get(LambdaUtil.getClass(getter));
        return new DatasetField(table, tableInfo.getFieldInfo(fieldName).getColumnName());
    }

    public <T> TableField field(Table table, Getter<T> getter) {
        String fieldName = LambdaUtil.getName(getter);
        TableInfo tableInfo = TableInfos.get(LambdaUtil.getClass(getter));
        return new TableField(table, tableInfo.getFieldInfo(fieldName).getColumnName());
    }

    public <T> Eq eq(Table firstTable, Getter<T> first, Table secondTable, Getter<T> second) {
        return super.eq(this.field(firstTable, first), this.field(secondTable, second));
    }

    public <T> Gt gt(Table firstTable, Getter<T> first, Table secondTable, Getter<T> second) {
        return super.gt(this.field(firstTable, first), this.field(secondTable, second));
    }
}
