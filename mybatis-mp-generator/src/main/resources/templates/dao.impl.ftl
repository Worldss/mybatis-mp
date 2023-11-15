package ${entityInfo.daoImplPackage};

<#list imports as pkg>
import ${pkg};
</#list>

/**
 * <p>
 * ${entityInfo.tableInfo.remarks!} Dao 实现类
 * </p>
 *
 * @author ${author!}
 * @since ${date}
 */
@Repository
public class ${entityInfo.daoImplName} ${superExtend}<#if daoConfig.isGeneric()><${entityInfo.name},<#if entityInfo.idFieldInfo??>${entityInfo.idFieldInfo.typeName}<#else>Void</#if>></#if> implements ${entityInfo.daoName}{

    @Autowired
    private ${entityInfo.daoImplName} (${entityInfo.mapperName} ${util.firstToLower(entityInfo.mapperName)}){
        super(${util.firstToLower(entityInfo.mapperName)});
    }
}
