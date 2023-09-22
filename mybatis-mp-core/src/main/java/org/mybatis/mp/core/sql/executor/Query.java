package org.mybatis.mp.core.sql.executor;

import db.sql.core.cmd.Select;
import db.sql.core.cmd.Table;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.util.Getter;

public class Query<R extends Object> extends db.sql.core.cmd.execution.Query<Query, MybatisCmdFactory> {

    public static final MybatisCmdFactory INSTANCE = new MybatisCmdFactory();

    private final Class<R> returnType;

    public Query(Class<R> returnType) {
        super(INSTANCE);
        this.returnType = returnType;
    }


    public Query select(Table table) {
        TableInfo tableInfo = TableInfos.get(table.getMappingClass());
        this.select(tableInfo.getTableFileds());
        return this;
    }

    public Select select(Table table, Getter getter) {
        return super.select($.field(table, getter));
    }

    public Select select(Getter getter) {
        if (this.from == null) {
            throw new RuntimeException("Please call the from method first");
        }
        return super.select($.field(this.from.getTable(), getter));
    }

    public Table from(Class tableClass) {
        return this.from(tableClass, null);
    }

    public Table from(Class tableClass, String columnPrefix) {
        TableInfo tableInfo = TableInfos.get(tableClass);
        super.from(tableInfo.getBasic().getTable());
        return tableInfo.getBasic().getTable();
    }

    public Class<R> getReturnType() {
        return returnType;
    }
}
