package ${entityInfo.mapperPackage};

<#list imports as pkg>
import ${pkg};
</#list>

/**
 * <p>
 * ${entityInfo.tableInfo.remarks!} Mapper 接口
 * </p>
 *
 * @author ${author!}
 * @since ${date}
 */
<#if mapperConfig.isMapperAnnotation()>
@Mapper
</#if>
public interface ${entityInfo.mapperName} ${superExtend}<${entityInfo.name}> {

}
