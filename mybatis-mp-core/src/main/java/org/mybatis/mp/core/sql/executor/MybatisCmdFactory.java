package org.mybatis.mp.core.sql.executor;

import db.sql.core.cmd.*;
import db.sql.core.cmd.condition.Eq;
import db.sql.core.cmd.condition.Gt;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.util.LambdaUtil;
import org.mybatis.mp.core.util.LambdaUtil.Getter;

public class MybatisCmdFactory extends CmdFactory {

    public Table table(Class tableClass) {
        Table table = new Table(TableInfos.get(tableClass).getBasic().getSchemaAndTableName());
        table.setMappingClass(tableClass);
        return table;
    }

    public DatasetField field(Dataset table, Getter getter) {
        String fieldName = LambdaUtil.getName(getter);
        TableInfo tableInfo = TableInfos.get(LambdaUtil.getClass(getter));
        return new DatasetField(table, tableInfo.getFieldInfo(fieldName).getColumnName());
    }

    public TableField field(Table table, Getter getter) {
        return (TableField) this.field((Dataset) table, getter);
    }

    public Eq eq(Table firstTable, Getter first, Table secondTable, Getter second) {
        return super.eq(this.field(firstTable, first), this.field(secondTable, second));
    }

    public Gt gt(Table firstTable, Getter first, Table secondTable, Getter second) {
        return super.gt(this.field(firstTable, first), this.field(secondTable, second));
    }
}