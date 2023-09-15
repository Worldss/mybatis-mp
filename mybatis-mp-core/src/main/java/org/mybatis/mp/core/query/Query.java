package org.mybatis.mp.core.query;

import db.sql.core.cmd.Select;
import db.sql.core.cmd.Table;
import org.mybatis.mp.core.util.LambdaUtil.Getter;

public class Query<R> extends db.sql.core.cmd.execution.Query<Query, MybatisCmdFactory> {

    public Query() {
        super(new MybatisCmdFactory());
    }

    public Select select(Table table, Getter getter) {
        return super.select($.field(table, getter));
    }

    public Table from(Class tableClass) {
        Table table = $.table(tableClass);
        super.from(table);
        return table;
    }
}
