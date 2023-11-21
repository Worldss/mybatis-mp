package cn.mybatis.mp.core.util;

import cn.mybatis.mp.core.db.reflect.ForeignInfo;
import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.sql.executor.MybatisCmdFactory;
import db.sql.core.api.cmd.struct.On;

import java.util.function.Consumer;

/**
 * 外键util
 */
public class ForeignKeyUtil {

    private ForeignKeyUtil() {
    }


    /**
     * 构建外键on的Consumer
     *
     * @param mybatisCmdFactory
     * @param mainTable
     * @param mainTableStorey
     * @param secondTable
     * @param secondTableStorey
     * @return
     */
    public static final Consumer<On> buildForeignKeyOnConsumer(MybatisCmdFactory mybatisCmdFactory, Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey) {
        return (on) -> {
            TableInfo mainTableInfo = Tables.get(mainTable);
            TableInfo secondTableInfo = Tables.get(secondTable);
            ForeignInfo foreignInfo;
            if ((foreignInfo = secondTableInfo.getForeignInfo(mainTable)) != null) {
                TableFieldInfo foreignFieldInfo = foreignInfo.getTableFieldInfo();
                on.eq(mybatisCmdFactory.field(mainTable, mainTableInfo.getIdFieldInfo().getField().getName(), mainTableStorey), mybatisCmdFactory.field(secondTable, foreignFieldInfo.getField().getName(), secondTableStorey));
            } else if ((foreignInfo = mainTableInfo.getForeignInfo(secondTable)) != null) {
                TableFieldInfo foreignFieldInfo = foreignInfo.getTableFieldInfo();
                on.eq(mybatisCmdFactory.field(secondTable, secondTableInfo.getIdFieldInfo().getField().getName(), secondTableStorey), mybatisCmdFactory.field(mainTable, foreignFieldInfo.getField().getName(), mainTableStorey));
            }
        };
    }
}
