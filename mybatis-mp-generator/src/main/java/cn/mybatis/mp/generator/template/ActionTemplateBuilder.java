package cn.mybatis.mp.generator.template;

import cn.mybatis.mp.generator.config.GeneratorConfig;
import cn.mybatis.mp.generator.database.meta.EntityInfo;
import cn.mybatis.mp.generator.util.GeneratorUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ActionTemplateBuilder extends AbstractTemplateBuilder {

    public ActionTemplateBuilder(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        super(generatorConfig, entityInfo);
    }

    @Override
public boolean enable() {
        return generatorConfig.getActionConfig().isEnable();
    }

    @Override
public String targetFilePath() {
        return generatorConfig.getBaseFilePath() + "/" + (entityInfo.getActionPackage() + "." + entityInfo.getActionName()).replaceAll("\\.", "/") + ".java";
    }

    @Override
public String templateFilePath() {
        return generatorConfig.getTemplateRootPath() + "/action";
    }

    @Override
public Map<String, Object> contextData() {
        Map<String, Object> data = new HashMap<>();
        data.put("imports", GeneratorUtil.buildActionImports(generatorConfig, entityInfo));
        if (generatorConfig.getActionConfig().getSuperClass() != null) {
            int dotIndex = generatorConfig.getActionConfig().getSuperClass().lastIndexOf(".");
            String superName;
            if (dotIndex > 0) {
                superName = generatorConfig.getActionConfig().getSuperClass().substring(dotIndex + 1);
            } else {
                superName = generatorConfig.getActionConfig().getSuperClass();
            }
            data.put("superExtend", "extends " + superName);
        } else {
            data.put("superExtend", "");
        }
        data.put("date", LocalDate.now().toString());
        data.put("author", generatorConfig.getAuthor());
        data.put("entityInfo", entityInfo);
        data.put("serviceConfig", generatorConfig.getServiceConfig());
        data.put("actionConfig", generatorConfig.getActionConfig());
        return data;
    }
}
