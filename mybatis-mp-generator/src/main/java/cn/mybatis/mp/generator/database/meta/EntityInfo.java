package cn.mybatis.mp.generator.database.meta;

import cn.mybatis.mp.generator.config.GeneratorConfig;
import cn.mybatis.mp.generator.util.GeneratorUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class EntityInfo {

    public final String serviceName;
    public final String servicePackage;
    public final String serviceImplName;
    public final String serviceImplPackage;
    public final String actionName;
    public final String actionPackage;
    private final TableInfo tableInfo;
    private final String name;
    private final String remarks;
    private final EntityFieldInfo idFieldInfo;
    private final List<EntityFieldInfo> fieldInfoList;
    private final List<EntityFieldInfo> excludeFieldInfoList;
    private final String entityPackage;
    private final String mapperName;
    private final String mapperPackage;
    private final String daoName;
    private final String daoPackage;
    private final String daoImplName;
    private final String daoImplPackage;
    private boolean hasIgnoreTablePrefix = false;

    public EntityInfo(GeneratorConfig generatorConfig, TableInfo tableInfo) {
        String tmpTableName = tableInfo.getName();
        for (String prefix : generatorConfig.getTableConfig().getTablePrefixs()) {
            if (tmpTableName.startsWith(prefix)) {
                tmpTableName = tmpTableName.replaceFirst(prefix, "");
                hasIgnoreTablePrefix = true;
                break;
            }
        }


        this.name = GeneratorUtil.getEntityName(generatorConfig, tmpTableName);
        this.remarks = tableInfo.getRemarks();
        this.tableInfo = tableInfo;
        if (tableInfo.getIdColumnInfo() != null) {
            this.idFieldInfo = new EntityFieldInfo(generatorConfig, this, tableInfo.getIdColumnInfo());
        } else {
            this.idFieldInfo = null;
        }
        List<EntityFieldInfo> fieldInfoList = tableInfo.getColumnInfoList().stream().map(item -> new EntityFieldInfo(generatorConfig, this, item)).collect(Collectors.toList());

        this.excludeFieldInfoList = fieldInfoList.stream().filter(item -> {
            return generatorConfig.getColumnConfig().getExcludeColumns().contains(item.getColumnInfo().getName()) || generatorConfig.getColumnConfig().getExcludeColumns().contains(item.getColumnInfo().getName().toUpperCase());
        }).collect(Collectors.toList());

        fieldInfoList.removeAll(this.excludeFieldInfoList);
        this.fieldInfoList = fieldInfoList;

        this.entityPackage = generatorConfig.getBasePackage() + "." + generatorConfig.getEntityConfig().getPackageName();

        this.mapperName = this.name + generatorConfig.getMapperConfig().getSuffix();
        this.mapperPackage = generatorConfig.getBasePackage() + "." + generatorConfig.getMapperConfig().getPackageName();

        this.daoName = this.name + generatorConfig.getDaoConfig().getSuffix();
        this.daoPackage = generatorConfig.getBasePackage() + "." + generatorConfig.getDaoConfig().getPackageName();

        this.daoImplName = this.name + generatorConfig.getDaoImplConfig().getSuffix();
        this.daoImplPackage = generatorConfig.getBasePackage() + "." + generatorConfig.getDaoImplConfig().getPackageName();

        this.serviceName = this.name + generatorConfig.getServiceConfig().getSuffix();
        this.servicePackage = generatorConfig.getBasePackage() + "." + generatorConfig.getServiceConfig().getPackageName();

        this.serviceImplName = this.name + generatorConfig.getServiceImplConfig().getSuffix();
        this.serviceImplPackage = generatorConfig.getBasePackage() + "." + generatorConfig.getServiceImplConfig().getPackageName();

        this.actionName = this.name + generatorConfig.getActionConfig().getSuffix();
        this.actionPackage = generatorConfig.getBasePackage() + "." + generatorConfig.getActionConfig().getPackageName();
    }

    public List<EntityFieldInfo> allFiledInfoList() {
        List<EntityFieldInfo> list = new ArrayList<>();
        if (this.idFieldInfo != null) {
            list.add(this.idFieldInfo);
        }
        list.addAll(this.excludeFieldInfoList);
        list.addAll(this.fieldInfoList);

        return list.stream().distinct().collect(Collectors.toList());
    }
}
