package cn.mybatis.mp.generator.template;

import cn.mybatis.mp.generator.config.GeneratorConfig;
import cn.mybatis.mp.generator.database.meta.EntityInfo;
import cn.mybatis.mp.generator.util.GeneratorUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MapperTemplateBuilder extends AbstractTemplateBuilder {

    public MapperTemplateBuilder(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        super(generatorConfig, entityInfo);
    }

    @Override    public String targetFilePath() {
        return generatorConfig.getBaseFilePath() + "/" + (entityInfo.getMapperPackage() + "." + entityInfo.getMapperName()).replaceAll("\\.", "/") + ".java";
    }

    @Override    public String templateFilePath() {
        return generatorConfig.getTemplateRootPath() + "/mapper";
    }

    @Override    public Map<String, Object> contextData() {
        Map<String, Object> data = new HashMap<>();
        data.put("imports", GeneratorUtil.buildMapperImports(generatorConfig, entityInfo));
        if (generatorConfig.getMapperConfig().getSuperClass() != null) {
            int dotIndex = generatorConfig.getMapperConfig().getSuperClass().lastIndexOf(".");
            String superName;
            if (dotIndex > 0) {
                superName = generatorConfig.getMapperConfig().getSuperClass().substring(dotIndex + 1);
            } else {
                superName = generatorConfig.getMapperConfig().getSuperClass();
            }
            data.put("superExtend", "extends " + superName);
        } else {
            data.put("superExtend", "");
        }
        data.put("date", LocalDate.now().toString());
        data.put("author",generatorConfig.getAuthor());
        data.put("entityInfo", entityInfo);
        data.put("mapperConfig", generatorConfig.getMapperConfig());
        return data;
    }
}
