package ${entityInfo.servicePackage};

<#list imports as pkg>
import ${pkg};
</#list>

/**
 * <p>
 * ${entityInfo.tableInfo.remarks!} Service 接口
 * </p>
 *
 * @author ${author!}
 * @since ${date}
 */
public interface ${entityInfo.serviceName} ${superExtend}<#if serviceConfig.isGeneric()><${entityInfo.name},<#if entityInfo.idFieldInfo??>${entityInfo.idFieldInfo.typeName}<#else>Void</#if>></#if> {

}
