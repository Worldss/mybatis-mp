package cn.mybatis.mp.generator;


import cn.mybatis.mp.generator.config.GeneratorConfig;
import cn.mybatis.mp.generator.database.meta.DatabaseMetaDataWrapper;
import cn.mybatis.mp.generator.database.meta.EntityInfo;
import cn.mybatis.mp.generator.database.meta.TableInfo;
import cn.mybatis.mp.generator.template.ITemplateBuilder;
import cn.mybatis.mp.generator.template.engine.FreemarkerTemplateEngine;
import cn.mybatis.mp.generator.template.engine.TemplateEngine;
import cn.mybatis.mp.generator.util.RuntimeUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FastGenerator {

    private GeneratorConfig generatorConfig;

    public FastGenerator(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
    }

    public void create() {
        List<EntityInfo> entityInfoList;
        try (Connection connection = generatorConfig.getDataSourceConfig().getConnection()) {
            DatabaseMetaDataWrapper databaseMetaDataWrapper = new DatabaseMetaDataWrapper(generatorConfig, connection);
            List<TableInfo> tableInfoList = databaseMetaDataWrapper.getTableInfoList(!generatorConfig.isIgnoreTable(), !generatorConfig.isIgnoreView());
            if (!generatorConfig.getExcludeTables().isEmpty() || !generatorConfig.getIncludeTables().isEmpty()) {
                tableInfoList = tableInfoList.stream().filter(tableInfo -> {
                    return !generatorConfig.getExcludeTables().stream().anyMatch(item -> item.equalsIgnoreCase(tableInfo.getName()) || Pattern.matches(item, tableInfo.getName().toLowerCase()));
                }).filter(tableInfo -> {
                    return generatorConfig.getIncludeTables().stream().anyMatch(item -> item.equalsIgnoreCase(tableInfo.getName()) || Pattern.matches(item, tableInfo.getName().toLowerCase()));
                }).collect(Collectors.toList());
            }
            entityInfoList = tableInfoList.stream().map(item -> new EntityInfo(generatorConfig, item)).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        TemplateEngine templateEngine = generatorConfig.getTemplateEngine();
        templateEngine = templateEngine == null ? new FreemarkerTemplateEngine() : templateEngine;
        for (EntityInfo entityInfo : entityInfoList) {
            for (Class<? extends ITemplateBuilder> templateBuilderClass : generatorConfig.getTemplateBuilders()) {
                ITemplateBuilder templateBuilder = null;
                try {
                    templateBuilder = templateBuilderClass.getConstructor(GeneratorConfig.class, EntityInfo.class).newInstance(generatorConfig, entityInfo);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (templateBuilder.enable()) {
                    templateEngine.render(templateBuilder);
                }
            }
        }

        if (generatorConfig.isFinishOpen()) {
            RuntimeUtils.openDir(generatorConfig.getBaseFilePath());
        }
    }
}
