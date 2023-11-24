package cn.mybatis.mp.generator.template;

import cn.mybatis.mp.generator.database.meta.EntityInfo;

import java.util.Map;

public interface ITemplateBuilder {

    default boolean enable() {
        return true;
    }

    EntityInfo getEntityInfo();

    String targetFilePath();

    String templateFilePath();

    Map<String, Object> contextData();

}
