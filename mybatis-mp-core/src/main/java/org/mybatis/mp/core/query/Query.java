package org.mybatis.mp.core.query;

import db.sql.core.SqlBuilderContext;
import db.sql.core.cmd.Cmd;
import db.sql.core.cmd.Select;
import db.sql.core.cmd.Table;
import db.sql.core.cmd.TableField;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.util.LambdaUtil.Getter;

import java.util.Objects;

public class Query<R> extends db.sql.core.cmd.execution.Query<Query, MybatisCmdFactory> {

    public Query() {
        super(new MybatisCmdFactory());
    }

    public Select select(Table table) {
        TableInfo tableInfo = TableInfos.get(table.getMappingClass());
        tableInfo.getFieldInfos().forEach(item -> {
            if (item.getFieldAnnotation().select()) {
                this.select.select($.field(table, item.getColumnName()));
            }
        });
        return this.select;
    }

    public Select select(Table table, Getter getter) {
        return super.select($.field(table, getter));
    }

    public Table from(Class tableClass) {
        return from(tableClass, null);
    }

    public Table from(Class tableClass, String columnPrefix) {
        Table table = $.table(tableClass).setPrefix(columnPrefix);
        table.setMappingClass(tableClass);
        super.from(table);
        return table;
    }

    @Override
    public StringBuilder sql(SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (this.select.getCmdList().isEmpty()) {
            if (Objects.nonNull(this.from) && this.from.getTable() instanceof Table) {
                Table table = (Table) this.from.getTable();
                if (Objects.nonNull(table.getMappingClass())) {
                    this.select(table);
                }
            }
        } else if (Objects.isNull(this.from)) {
            for (Cmd cmd : this.getCmdList()) {
                if (cmd instanceof TableField) {
                    TableField tableField = (TableField) cmd;
                    this.from(tableField.getTable());
                    break;
                }
            }
        }
        return super.sql(context, sqlBuilder);
    }
}
