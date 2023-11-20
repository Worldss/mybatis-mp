package cn.mybatis.mp.core.db.reflect;

import cn.mybatis.mp.core.util.FieldUtils;
import cn.mybatis.mp.core.util.GenericUtil;
import cn.mybatis.mp.db.annotations.Table;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModelInfo {

    /**
     * 对应的类
     */
    private final Class<?> type;

    /**
     * 表信息
     */
    private final TableInfo tableInfo;

    /**
     * 所有 字段
     */
    private final List<ModelFieldInfo> modelFieldInfos;

    /**
     * ID 字段信息
     */
    private final ModelFieldInfo idFieldInfo;

    /**
     * 乐观锁字段
     */
    private final ModelFieldInfo versionFieldInfo;

    /**
     * 多租户ID
     */
    private final ModelFieldInfo tenantIdFieldInfo;


    public ModelInfo(Class<?> model) {
        this.type = model;
        Class<?> entity = GenericUtil.getGenericInterfaceClass(model).stream().filter(item -> item.isAnnotationPresent(Table.class)).findFirst().orElseThrow(() -> {
            return new RuntimeException(MessageFormat.format("class {0} have no generic type", model.getName()));
        });

        this.tableInfo = Tables.get(entity);
        if (Objects.isNull(tableInfo)) {
            throw new RuntimeException(MessageFormat.format("unable match model class {0} , the generic class {1} is not a entity", model.getName(), entity.getName()));
        }

        List<ModelFieldInfo> modelFieldInfos = FieldUtils.getResultMappingFields(model).stream().map(field -> {
            return new ModelFieldInfo(entity, model, field);
        }).collect(Collectors.toList());

        this.idFieldInfo = modelFieldInfos.stream().filter(item -> item.getTableFieldInfo().isTableId()).findFirst().orElse(null);
        this.versionFieldInfo = modelFieldInfos.stream().filter(item -> item.getTableFieldInfo().isVersion()).findFirst().orElse(null);
        this.tenantIdFieldInfo = modelFieldInfos.stream().filter(item -> item.getTableFieldInfo().isTenantId()).findFirst().orElse(null);

        this.modelFieldInfos = Collections.unmodifiableList(modelFieldInfos);
    }

    public Class<?> getType() {
        return type;
    }


    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public ModelFieldInfo getIdFieldInfo() {
        return idFieldInfo;
    }

    public ModelFieldInfo getVersionFieldInfo() {
        return versionFieldInfo;
    }

    public ModelFieldInfo getTenantIdFieldInfo() {
        return tenantIdFieldInfo;
    }

    public List<ModelFieldInfo> getModelFieldInfos() {
        return modelFieldInfos;
    }
}
