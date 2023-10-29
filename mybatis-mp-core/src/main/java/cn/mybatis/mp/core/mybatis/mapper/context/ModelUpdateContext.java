package cn.mybatis.mp.core.mybatis.mapper.context;


import cn.mybatis.mp.core.db.reflect.ModelInfo;
import cn.mybatis.mp.core.db.reflect.Models;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.mybatis.configuration.MybatisParameter;
import cn.mybatis.mp.core.sql.executor.Update;
import cn.mybatis.mp.db.Model;
import cn.mybatis.mp.db.annotations.TableField;
import db.sql.core.api.cmd.Table;

import java.text.MessageFormat;
import java.util.Objects;

public class ModelUpdateContext<T extends Model> extends SQLCmdUpdateContext {

    private final T value;

    public ModelUpdateContext(T t) {
        super(createCmd(t));
        this.value = t;
    }

    private static Update createCmd(Object t) {
        ModelInfo modelInfo = Models.get(t.getClass());
        Update update = new Update() {{
            Table table = $.table(modelInfo.getTableInfo().getSchemaAndTableName());
            update(table);
            modelInfo.getModelFieldInfos().stream().forEach(item -> {
                Object value = item.getValue(t);
                if (item.getTableFieldInfo().isTableId()) {
                    if (Objects.isNull(value)) {
                        throw new RuntimeException(item.getField().getName() + " can't be null");
                    }
                    eq($.field(table, item.getTableFieldInfo().getColumnName()), $.value(value));
                } else if (Objects.nonNull(value)) {
                    TableField tableField = item.getTableFieldInfo().getFieldAnnotation();
                    MybatisParameter mybatisParameter = new MybatisParameter(value, tableField.typeHandler(), tableField.jdbcType());
                    set($.field(table, item.getTableFieldInfo().getColumnName()), $.value(mybatisParameter));
                }
            });
        }};
        if (update.getWhere() == null || !update.getWhere().conditionChain().hasContent()) {
            throw new RuntimeException(MessageFormat.format("model {0} can't found id", t.getClass()));
        }
        return update;
    }


}
