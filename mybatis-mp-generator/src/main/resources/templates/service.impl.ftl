package ${entityInfo.serviceImplPackage};

<#list imports as pkg>
import ${pkg};
</#list>

/**
 * <p>
 * ${entityInfo.tableInfo.remarks!} Service 实现类
 * </p>
 *
 * @author ${author!}
 * @since ${date}
 */
@Service
public class ${entityInfo.serviceImplName} ${superExtend}<#if serviceConfig.isGeneric()><${entityInfo.name},<#if entityInfo.idFieldInfo??>${entityInfo.idFieldInfo.typeName}<#else>Void</#if>></#if> implements ${entityInfo.serviceName}{

<#if serviceImplConfig.isInjectDao()>
    @Autowired
    private ${entityInfo.daoName} ${util.firstToLower(entityInfo.daoName)};
</#if>
}
