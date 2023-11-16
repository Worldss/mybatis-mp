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
<#if serviceImplConfig.isInjectDao()>
    @Autowired
    private ${entityInfo.mapperName} ${util.firstToLower(entityInfo.mapperName)};

    private QueryChain queryChain() {
        return new QueryChain(${util.firstToLower(entityInfo.mapperName)});
    }

    private UpdateChain updateChain() {
        return new UpdateChain(${util.firstToLower(entityInfo.mapperName)});
    }

    private InsertChain insertChain(){
        return new InsertChain(${util.firstToLower(entityInfo.mapperName)});
    }

    private DeleteChain deleteChain(){
        return new DeleteChain(${util.firstToLower(entityInfo.mapperName)});
    }

</#if>
}
