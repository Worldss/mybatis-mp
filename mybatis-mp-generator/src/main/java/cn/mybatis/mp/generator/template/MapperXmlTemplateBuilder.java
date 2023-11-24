package cn.mybatis.mp.generator.template;

import cn.mybatis.mp.generator.config.GeneratorConfig;
import cn.mybatis.mp.generator.database.meta.EntityInfo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MapperXmlTemplateBuilder extends AbstractTemplateBuilder {

    public MapperXmlTemplateBuilder(GeneratorConfig generatorConfig, EntityInfo entityInfo) {
        super(generatorConfig, entityInfo);
    }

    @Override
    public boolean enable() {
        return generatorConfig.getMapperXmlConfig().isEnable();
    }

    @Override
    public String targetFilePath() {
        return generatorConfig.getBaseFilePath() + "/" + (generatorConfig.getMapperXmlConfig().getPackageName() + "." + entityInfo.getName() + generatorConfig.getMapperXmlConfig().getSuffix()).replaceAll("\\.", "/") + ".xml";
    }

    @Override
    public String templateFilePath() {
        return generatorConfig.getTemplateRootPath() + "/mapper.xml";
    }

    @Override
    public Map<String, Object> contextData() {
        Map<String, Object> data = new HashMap<>();
        data.put("date", LocalDate.now().toString());
        data.put("author", generatorConfig.getAuthor());
        data.put("entityInfo", entityInfo);
        data.put("mapperConfig", generatorConfig.getMapperConfig());
        data.put("mapperXmlConfig", generatorConfig.getMapperXmlConfig());
        return data;
    }
}
