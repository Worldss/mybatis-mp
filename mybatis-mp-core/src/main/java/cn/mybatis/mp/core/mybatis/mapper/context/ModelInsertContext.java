package cn.mybatis.mp.core.mybatis.mapper.context;

import cn.mybatis.mp.core.db.reflect.ModelFieldInfo;
import cn.mybatis.mp.core.db.reflect.ModelInfo;
import cn.mybatis.mp.core.db.reflect.Models;
import cn.mybatis.mp.core.db.reflect.TableIds;
import cn.mybatis.mp.core.incrementer.IdentifierGenerator;
import cn.mybatis.mp.core.incrementer.IdentifierGeneratorFactory;
import cn.mybatis.mp.core.mybatis.configuration.MybatisParameter;
import cn.mybatis.mp.core.tenant.TenantUtil;
import cn.mybatis.mp.db.IdAutoType;
import cn.mybatis.mp.db.Model;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.TableId;
import db.sql.core.api.cmd.basic.Table;
import db.sql.core.api.cmd.executor.AbstractInsert;
import db.sql.core.api.cmd.executor.Insert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModelInsertContext<T extends Model> extends SQLCmdInsertContext<AbstractInsert> implements SetIdMethod {

    private final T value;

    public ModelInsertContext(T t) {
        super(createCmd(t));
        this.value = t;
    }

    private static Insert createCmd(Model model) {
        //设置租户ID
        TenantUtil.setTenantId(model);
        ModelInfo modelInfo = Models.get(model.getClass());
        Insert insert = new Insert() {{
            Table table = $.table(modelInfo.getTableInfo().getSchemaAndTableName());
            insert(table);
            List<Object> values = new ArrayList<>();
            modelInfo.getModelFieldInfos().stream().forEach(item -> {
                boolean isNeedInsert = false;
                Object value = item.getValue(model);
                if (Objects.nonNull(value)) {
                    isNeedInsert = true;
                } else if (item.getTableFieldInfo().isVersion()) {
                    isNeedInsert = true;
                    try {
                        //乐观锁设置 默认值1
                        value = Integer.valueOf(1);
                        item.getWriteFieldInvoker().invoke(model, new Object[]{value});
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else if (item.getTableFieldInfo().isTableId()) {
                    TableId tableId = TableIds.get(modelInfo.getTableInfo().getType());
                    if (tableId.value() == IdAutoType.GENERATOR) {
                        isNeedInsert = true;
                        IdentifierGenerator identifierGenerator = IdentifierGeneratorFactory.getIdentifierGenerator(tableId.generatorName());
                        Object id = identifierGenerator.nextId(modelInfo.getType());
                        if (modelInfo.getIdFieldInfo().getField().getType() == String.class) {
                            id = id instanceof String ? id : String.valueOf(id);
                        }
                        if (setId(model, item, id)) {
                            value = id;
                        }
                    }
                }

                if (isNeedInsert) {
                    field($.field(table, item.getTableFieldInfo().getColumnName()));
                    TableField tableField = item.getTableFieldInfo().getTableFieldAnnotation();
                    MybatisParameter mybatisParameter = new MybatisParameter(value, tableField.typeHandler(), tableField.jdbcType());
                    values.add($.value(mybatisParameter));
                }
            });
            values(values);
        }};
        return insert;
    }


    private static boolean setId(Object obj, ModelFieldInfo idFieldInfo, Object id) {
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
        ModelInfo modelInfo = Models.get(this.value.getClass());
        setId(this.value, modelInfo.getIdFieldInfo(), id);
    }
}
