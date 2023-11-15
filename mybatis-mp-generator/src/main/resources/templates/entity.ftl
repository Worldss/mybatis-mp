package ${entityInfo.entityPackage};

<#list imports as pkg>
import ${pkg};
</#list>

/**
 * <p>
 * ${entityInfo.tableInfo.remarks!}
 * </p>
 *
 * @author ${author!}
 * @since ${date}
 */
<#if entityConfig.isLombok()>@Data</#if>
@Table<#if entityConfig.isSchema()>(schema="${entityInfo.tableInfo.schema!}")</#if>
public class ${entityInfo.name} ${superExtend}{

<#list entityInfo.fieldInfoList as field>
    /**
     * <p>
     * ${field.remarks!}
     * </p>
     */
<#if field.columnInfo.primaryKey??>
    ${field.buildTableIdCode()!}
    private ${field.typeName} ${field.name};
<#else>
    <#if field.isNeedTableFiled()>${field.buildTableField()}</#if>
    private ${field.typeName} ${field.name};
</#if>

</#list>
<#if entityConfig.isLombok() == false>
<#list entityInfo.fieldInfoList as field>
    public void ${field.setterMethodName()} (${field.typeName} ${field.name}) {
        this.${field.name} = ${field.name};
    }

    public ${field.typeName} ${field.getterMethodName()} () {
        return this.${field.name};
    }

</#list>
</#if>
}
