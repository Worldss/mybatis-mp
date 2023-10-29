package cn.mybatis.mp.core.sql.executor;

import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import db.sql.api.Getter;
import db.sql.core.api.cmd.CmdFactory;
import db.sql.core.api.cmd.Table;
import db.sql.core.api.cmd.TableField;
import db.sql.core.api.tookit.LambdaUtil;
import org.apache.ibatis.util.MapUtil;

import java.util.Objects;


public class MybatisCmdFactory extends CmdFactory {
    public Table table(Class entity) {
        return this.table(entity, 1);
    }

    public Table cacheTable(Class entity, int storey) {
        return this.tableCache.get(String.format("%s.%s", entity.getName(), storey));
    }

    @Override
    public Table table(Class entity, int storey) {
        return MapUtil.computeIfAbsent(this.tableCache, String.format("%s.%s", entity.getName(), storey), key -> {
            TableInfo tableInfo = Tables.get(entity);
            tableNums++;
            Table table = new Table(tableInfo.getSchemaAndTableName());
            table.as(tableAs(storey, tableNums));
            return table;
        });
    }

    @Override
    public <T> TableField field(Getter<T> getter, int storey) {
        Class clazz = LambdaUtil.getClass(getter);
        String filedName = LambdaUtil.getName(getter);
        return this.field(clazz, filedName, storey);
    }

    public TableField field(Class clazz, String filedName, int storey) {
        return MapUtil.computeIfAbsent(tableFieldCache, String.format("%s.%s.%s", clazz.getName(), filedName, storey), key -> {
            Table table = table(clazz, storey);
            TableInfo tableInfo = Tables.get(clazz);
            TableFieldInfo tableFieldInfo = tableInfo.getFieldInfo(filedName);
            if (Objects.isNull(tableFieldInfo)) {
                throw new RuntimeException(String.format("property %s is not a column", filedName));
            }
            return new TableField(table, tableFieldInfo.getColumnName());
        });
    }

}
