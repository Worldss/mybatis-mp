package cn.mybatis.mp.generator.database.meta;

import cn.mybatis.mp.core.util.NamingUtil;
import cn.mybatis.mp.generator.config.GeneratorConfig;
import cn.mybatis.mp.generator.util.GeneratorUtil;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EntityFieldInfo {

    @ToString.Exclude
    private final EntityInfo entityInfo;

    private final ColumnInfo columnInfo;

    private final String name;

    private final Class<?> type;

    private final String typeName;

    private final String remarks;

    private final boolean select;

    private final boolean update;

    private final String defaultTableIdCode;

    public EntityFieldInfo(GeneratorConfig generatorConfig, EntityInfo entityInfo, ColumnInfo columnInfo) {
        this.entityInfo = entityInfo;
        this.columnInfo = columnInfo;
        this.update = !generatorConfig.getColumnConfig().getDisableUpdateColumns().contains(columnInfo.getName());
        this.name = GeneratorUtil.getEntityFieldName(generatorConfig, columnInfo);
        this.remarks = GeneratorUtil.getEntityFieldRemarks(generatorConfig, columnInfo);
        this.type = GeneratorUtil.getColumnType(generatorConfig, columnInfo);
        if (this.type == byte[].class) {
            this.select = false;
        } else {
            this.select = !generatorConfig.getColumnConfig().getDisableSelectColumns().contains(columnInfo.getName());
        }
        this.typeName = this.type.getSimpleName();
        this.defaultTableIdCode = generatorConfig.getEntityConfig().getDefaultTableIdCode();
    }

    public boolean isNeedTableFiled() {
        return !select || !update || this.getColumnInfo().getDefaultValue() != null;
    }

    public String buildTableField() {
        StringBuilder stringBuilder = new StringBuilder("@TableField(");
        if (!select) {
            stringBuilder.append("select = false,");
        }
        if (!update) {
            stringBuilder.append("update = false,");
        }
        if (this.getColumnInfo().getDefaultValue() != null) {
            stringBuilder.append(String.format("defaultValue = \"%s\",", this.getColumnInfo().getDefaultValue().replace("\"", "\\\"")));
        }
        stringBuilder = stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public String getterMethodName() {
        return (this.type == Boolean.class ? "is" : "get") + NamingUtil.firstToUpperCase(this.name);
    }

    public String setterMethodName() {
        return "set" + NamingUtil.firstToUpperCase(this.name);
    }

    public String buildTableIdCode() {
        if (!this.columnInfo.isPrimaryKey()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder("@TableId");
        if (this.columnInfo.isAutoIncrement()) {
            stringBuilder.append("(IdAutoType.AUTO)");
        } else {
            if (this.defaultTableIdCode != null) {
                return this.defaultTableIdCode;
            }
            stringBuilder.append("(IdAutoType.NONE)");
        }
        return stringBuilder.toString();
    }
}
