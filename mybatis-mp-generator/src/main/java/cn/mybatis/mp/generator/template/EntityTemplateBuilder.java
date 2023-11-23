package cn.mybatis.mp.generator.template;

import cn.mybatis.mp.generator.config.GeneratorConfig;
import cn.mybatis.mp.generator.database.meta.EntityInfo;
import cn.mybatis.mp.generator.util.GeneratorUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EntityTemplateBuilder extends AbstractTemplateBuilder {

    public EntityTemplateBuilder(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        super(generatorConfig, entityInfo);
    }

    @Override
public String targetFilePath() {
        return generatorConfig.getBaseFilePath() + "/" + (entityInfo.getEntityPackage() + "." + entityInfo.getName()).replaceAll("\\.", "/") + ".java";
    }

    @Override
public String templateFilePath() {
        return generatorConfig.getTemplateRootPath() + "/entity";
    }

    @Override
public Map<String, Object> contextData() {
        Map<String, Object> data = new HashMap<>();
        data.put("imports", GeneratorUtil.buildEntityImports(generatorConfig, entityInfo));
        if (generatorConfig.getEntityConfig().getSuperClass() != null) {
            int dotIndex = generatorConfig.getEntityConfig().getSuperClass().lastIndexOf(".");
            String superName;
            if (dotIndex > 0) {
                superName = generatorConfig.getEntityConfig().getSuperClass().substring(dotIndex + 1);
            } else {
                superName = generatorConfig.getEntityConfig().getSuperClass();
            }
            data.put("superExtend", "extends " + superName);
        } else {
            data.put("superExtend", "");
        }
        data.put("package",entityInfo.getEntityPackage());
        data.put("date", LocalDate.now().toString());
        data.put("author",generatorConfig.getAuthor());
        data.put("entityInfo", entityInfo);
        data.put("entityConfig", generatorConfig.getEntityConfig());
        return data;
    }
}
