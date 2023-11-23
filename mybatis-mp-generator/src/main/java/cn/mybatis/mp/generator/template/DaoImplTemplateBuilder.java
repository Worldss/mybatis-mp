package cn.mybatis.mp.generator.template;

import cn.mybatis.mp.generator.config.GeneratorConfig;
import cn.mybatis.mp.generator.database.meta.EntityInfo;
import cn.mybatis.mp.generator.util.GeneratorUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DaoImplTemplateBuilder extends AbstractTemplateBuilder {

    public DaoImplTemplateBuilder(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        super(generatorConfig, entityInfo);
    }

    @Override
public boolean enable() {
        return generatorConfig.getDaoConfig().isEnable();
    }

    @Override
public String targetFilePath() {
        return generatorConfig.getBaseFilePath() + "/" + (entityInfo.getDaoImplPackage() + "." + entityInfo.getDaoImplName()).replaceAll("\\.", "/") + ".java";
    }

    @Override
public String templateFilePath() {
        return generatorConfig.getTemplateRootPath() + "/dao.impl";
    }

    @Override
public Map<String, Object> contextData() {
        Map<String, Object> data = new HashMap<>();
        data.put("imports", GeneratorUtil.buildDaoImplImports(generatorConfig, entityInfo));
        if (generatorConfig.getDaoImplConfig().getSuperClass() != null) {
            int dotIndex = generatorConfig.getDaoImplConfig().getSuperClass().lastIndexOf(".");
            String superName;
            if (dotIndex > 0) {
                superName = generatorConfig.getDaoImplConfig().getSuperClass().substring(dotIndex + 1);
            } else {
                superName = generatorConfig.getDaoImplConfig().getSuperClass();
            }
            data.put("superExtend", "extends " + superName);
        } else {
            data.put("superExtend", "");
        }
        data.put("date", LocalDate.now().toString());
        data.put("author", generatorConfig.getAuthor());
        data.put("entityInfo", entityInfo);
        data.put("daoConfig", generatorConfig.getDaoConfig());
        data.put("daoImplConfig", generatorConfig.getDaoImplConfig());
        return data;
    }
}
