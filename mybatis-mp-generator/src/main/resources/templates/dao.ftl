package ${entityInfo.daoPackage};

<#list imports as pkg>
import ${pkg};
</#list>

/**
 * <p>
 * ${entityInfo.tableInfo.remarks!} Dao 接口
 * </p>
 *
 * @author ${author!}
 * @since ${date}
 */
public interface ${entityInfo.daoName} ${superExtend}<#if daoConfig.isGeneric()><${entityInfo.name},<#if entityInfo.idFieldInfo??>${entityInfo.idFieldInfo.typeName}<#else>Void</#if>></#if> {

}
