package cn.mybatis.mp.generator.template;

import cn.mybatis.mp.generator.config.GeneratorConfig;
import cn.mybatis.mp.generator.database.meta.EntityInfo;
import cn.mybatis.mp.generator.util.GeneratorUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ServiceTemplateBuilder extends AbstractTemplateBuilder {

    public ServiceTemplateBuilder(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        super(generatorConfig, entityInfo);
    }

    @Override
    public boolean enable() {
        return generatorConfig.getServiceConfig().isEnable();
    }

    @Override
    public String targetFilePath() {
        return generatorConfig.getBaseFilePath() + "/" + (entityInfo.getServicePackage() + "." + entityInfo.getServiceName()).replaceAll("\\.", "/") + ".java";
    }

    @Override
    public String templateFilePath() {
        return generatorConfig.getTemplateRootPath() + "/service";
    }

    @Override
    public Map<String, Object> contextData() {
        Map<String, Object> data = new HashMap<>();
        data.put("imports", GeneratorUtil.buildServiceImports(generatorConfig, entityInfo));
        if (generatorConfig.getServiceConfig().getSuperClass() != null) {
            int dotIndex = generatorConfig.getServiceConfig().getSuperClass().lastIndexOf(".");
            String superName;
            if (dotIndex > 0) {
                superName = generatorConfig.getServiceConfig().getSuperClass().substring(dotIndex + 1);
            } else {
                superName = generatorConfig.getServiceConfig().getSuperClass();
            }
            data.put("superExtend", "extends " + superName);
        } else {
            data.put("superExtend", "");
        }
        data.put("date", LocalDate.now().toString());
        data.put("author", generatorConfig.getAuthor());
        data.put("entityInfo", entityInfo);
        data.put("daoConfig", generatorConfig.getDaoConfig());
        data.put("serviceConfig", generatorConfig.getServiceConfig());
        return data;
    }
}
