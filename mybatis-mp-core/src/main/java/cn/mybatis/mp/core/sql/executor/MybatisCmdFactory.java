package cn.mybatis.mp.core.sql.executor;

import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import db.sql.api.Getter;
import db.sql.api.impl.cmd.CmdFactory;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.impl.tookit.LambdaUtil;
import org.apache.ibatis.util.MapUtil;

import java.util.Objects;


/**
 * CMD 命令工厂
 * 增加了对实体类的映射
 */
public class MybatisCmdFactory extends CmdFactory {

    public MybatisCmdFactory() {
        super();
    }

    public MybatisCmdFactory(String tableAsPrefix) {
        super(tableAsPrefix);
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


    @Override
    public <T> String columnName(Getter<T> getter) {
        Class entity = LambdaUtil.getClass(getter);
        TableInfo tableInfo = Tables.get(entity);
        if (tableInfo == null) {
            throw new RuntimeException(String.format("class %s is not entity", entity.getName()));
        }
        String filedName = LambdaUtil.getName(getter);
        TableFieldInfo tableFieldInfo = tableInfo.getFieldInfo(filedName);
        if (Objects.isNull(tableFieldInfo)) {
            throw new RuntimeException(String.format("property %s is not a column", filedName));
        }
        return tableFieldInfo.getColumnName();
    }

    public TableField field(Class entity, String filedName, int storey) {
        return MapUtil.computeIfAbsent(tableFieldCache, String.format("%s.%s.%s", entity.getName(), filedName, storey), key -> {
            TableInfo tableInfo = Tables.get(entity);
            if (tableInfo == null) {
                throw new RuntimeException(String.format("class %s is not entity", entity.getName()));
            }
            TableFieldInfo tableFieldInfo = tableInfo.getFieldInfo(filedName);
            if (Objects.isNull(tableFieldInfo)) {
                throw new RuntimeException(String.format("property %s is not a column", filedName));
            }
            Table table = table(entity, storey);
            return new TableField(table, tableFieldInfo.getColumnName());
        });
    }

}
