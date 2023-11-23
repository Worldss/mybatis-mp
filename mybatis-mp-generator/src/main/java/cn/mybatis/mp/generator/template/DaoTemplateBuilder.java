package cn.mybatis.mp.generator.template;

import cn.mybatis.mp.generator.config.GeneratorConfig;
import cn.mybatis.mp.generator.database.meta.EntityInfo;
import cn.mybatis.mp.generator.util.GeneratorUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DaoTemplateBuilder extends AbstractTemplateBuilder {

    public DaoTemplateBuilder(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        super(generatorConfig, entityInfo);
    }

    @Override    public boolean enable() {
        return generatorConfig.getDaoConfig().isEnable();
    }

    @Override    public String targetFilePath() {
        return generatorConfig.getBaseFilePath() + "/" + (entityInfo.getDaoPackage() + "." + entityInfo.getDaoName()).replaceAll("\\.", "/") + ".java";
    }

    @Override    public String templateFilePath() {
        return generatorConfig.getTemplateRootPath() + "/dao";
    }

    @Override    public Map<String, Object> contextData() {
        Map<String, Object> data = new HashMap<>();
        data.put("imports", GeneratorUtil.buildDaoImports(generatorConfig, entityInfo));
        if (generatorConfig.getDaoConfig().getSuperClass() != null) {
            int dotIndex = generatorConfig.getDaoConfig().getSuperClass().lastIndexOf(".");
            String superName;
            if (dotIndex > 0) {
                superName = generatorConfig.getDaoConfig().getSuperClass().substring(dotIndex + 1);
            } else {
                superName = generatorConfig.getDaoConfig().getSuperClass();
            }
            data.put("superExtend", "extends " + superName);
        } else {
            data.put("superExtend", "");
        }
        data.put("date", LocalDate.now().toString());
        data.put("author", generatorConfig.getAuthor());
        data.put("entityInfo", entityInfo);
        data.put("daoConfig", generatorConfig.getDaoConfig());
        return data;
    }
}
