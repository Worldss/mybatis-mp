package cn.mybatis.mp.generator.template;

import cn.mybatis.mp.generator.config.GeneratorConfig;
import cn.mybatis.mp.generator.database.meta.EntityInfo;

public abstract class AbstractTemplateBuilder implements ITemplateBuilder {

    protected final GeneratorConfig generatorConfig;

    protected final EntityInfo entityInfo;

    public AbstractTemplateBuilder(GeneratorConfig generatorConfig, EntityInfo entityInfo){
        this.generatorConfig=generatorConfig;
        this.entityInfo=entityInfo;
    }

    @Override
    public EntityInfo getEntityInfo() {
        return entityInfo;
    }
}
