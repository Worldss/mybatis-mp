package cn.mybatis.mp.core.mybatis.mapper.context;

import cn.mybatis.mp.core.MybatisMpConfig;
import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableIds;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.incrementer.IdentifierGenerator;
import cn.mybatis.mp.core.incrementer.IdentifierGeneratorFactory;
import cn.mybatis.mp.core.tenant.TenantUtil;
import cn.mybatis.mp.core.util.StringPool;
import cn.mybatis.mp.db.IdAutoType;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.TableId;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.cmd.executor.AbstractInsert;
import db.sql.api.impl.cmd.executor.Insert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EntityInsertContext<T> extends SQLCmdInsertContext<AbstractInsert> implements SetIdMethod {

    private final T value;

    public EntityInsertContext(T t) {
        super(createCmd(t));
        this.value = t;
    }

    private static Insert createCmd(Object t) {
        //设置租户ID
        TenantUtil.setTenantId(t);

        TableInfo tableInfo = Tables.get(t.getClass());
        Insert insert = new Insert() {{
            Table table = $.table(tableInfo.getSchemaAndTableName());
            insert(table);
            List<Object> values = new ArrayList<>();
            for (int i = 0; i < tableInfo.getFieldSize(); i++) {
                TableFieldInfo tableFieldInfo = tableInfo.getTableFieldInfos().get(i);
                boolean isNeedInsert = false;
                Object value = tableFieldInfo.getValue(t);
                if (Objects.nonNull(value)) {
                    isNeedInsert = true;
                } else if (tableFieldInfo.isTableId()) {
                    TableId tableId = TableIds.get(t.getClass());
                    if (tableId.value() == IdAutoType.GENERATOR) {
                        isNeedInsert = true;
                        IdentifierGenerator identifierGenerator = IdentifierGeneratorFactory.getIdentifierGenerator(tableId.generatorName());
                        Object id = identifierGenerator.nextId(tableInfo.getType());
                        if (setId(t, tableFieldInfo, id)) {
                            value = id;
                        }
                    }
                } else if (!StringPool.EMPTY.equals(tableFieldInfo.getTableFieldAnnotation().defaultValue())) {
                    isNeedInsert = true;
                    try {
                        //设置默认值
                        value = MybatisMpConfig.getDefaultValue(tableFieldInfo.getField().getType(), tableFieldInfo.getTableFieldAnnotation().defaultValue());
                        tableFieldInfo.getWriteFieldInvoker().invoke(t, new Object[]{value});
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else if (tableFieldInfo.isVersion()) {
                    isNeedInsert = true;
                    try {
                        //乐观锁设置 默认值1
                        value = Integer.valueOf(1);
                        tableFieldInfo.getWriteFieldInvoker().invoke(t, new Object[]{value});
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (isNeedInsert) {
                    field($.field(table, tableFieldInfo.getColumnName()));
                    TableField tableField = tableFieldInfo.getTableFieldAnnotation();
                    MybatisParameter mybatisParameter = new MybatisParameter(value, tableField.typeHandler(), tableField.jdbcType());
                    values.add($.value(mybatisParameter));
                }
            }
            values(values);
        }};
        return insert;
    }

    private static boolean setId(Object obj, TableFieldInfo idFieldInfo, Object id) {
        try {
            //如果设置了id 则不在设置
            if (idFieldInfo.getReadFieldInvoker().invoke(obj, null) != null) {
                return false;
            }
            if (idFieldInfo.getField().getType() == String.class) {
                id = id instanceof String ? id : String.valueOf(id);
            }
            idFieldInfo.getWriteFieldInvoker().invoke(obj, new Object[]{id});
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void setId(Object id) {
        TableFieldInfo idFieldInfo = Tables.get(this.value.getClass()).getIdFieldInfo();
        setId(this.value, idFieldInfo, id);
    }
}
