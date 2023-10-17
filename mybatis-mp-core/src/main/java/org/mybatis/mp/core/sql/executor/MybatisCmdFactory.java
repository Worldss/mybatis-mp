package org.mybatis.mp.core.sql.executor;

import db.sql.core.api.cmd.*;
import db.sql.core.api.cmd.condition.Eq;
import db.sql.core.api.cmd.condition.Gt;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;


public class MybatisCmdFactory extends CmdFactory {

    public static final MybatisCmdFactory INSTANCE = new MybatisCmdFactory();

    public Table table(Class tableClass) {
        Table table = new Table(TableInfos.get(tableClass).getBasic().getSchemaAndTableName());
        return table;
    }

}
