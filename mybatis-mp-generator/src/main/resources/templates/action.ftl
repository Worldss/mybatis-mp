package ${entityInfo.actionPackage};

<#list imports as pkg>
import ${pkg};
</#list>

/**
 * <p>
 * ${entityInfo.tableInfo.remarks!} 控制器
 * </p>
 *
 * @author ${author!}
 * @since ${date}
 */
@RestController
@RequestMapping("/${util.firstToLower(entityInfo.daoName)}")
public class ${entityInfo.actionName} ${superExtend}<#if actionConfig.isGeneric()><${entityInfo.name},<#if entityInfo.idFieldInfo??>${entityInfo.idFieldInfo.typeName}<#else>Void</#if>></#if>{

<#if actionConfig.isInjectService() && serviceConfig.isEnable()>
    @Autowired
    private ${entityInfo.serviceName} ${util.firstToLower(entityInfo.serviceName)};

</#if>
<#if actionConfig.isGetById()  && entityInfo.idFieldInfo??>
    @GetMapping("/get")
    public ${actionConfig.returnClassName} get(${entityInfo.idFieldInfo.typeName} id){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

</#if>
<#if actionConfig.isSave()>
    @PostMapping("/save")
    public ${actionConfig.returnClassName} save(${entityInfo.name} ${util.firstToLower(entityInfo.name)}){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

</#if>
<#if actionConfig.isUpdate()>
    @PostMapping("/update")
    public ${actionConfig.returnClassName} update(${entityInfo.name} ${util.firstToLower(entityInfo.name)}){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

</#if>
<#if actionConfig.isDeleteById() && entityInfo.idFieldInfo??>
    @GetMapping("/delete")
    public ${actionConfig.returnClassName} delete(${entityInfo.idFieldInfo.typeName} id){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

</#if>
<#if actionConfig.isFind()>
    @GetMapping("/find")
    public ${actionConfig.returnClassName} find(Pager<${entityInfo.name}> pager){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

</#if>
}
