package cn.mybatis.mp.generator.template.engine;

import cn.mybatis.mp.generator.template.ITemplateBuilder;

public interface TemplateEngine {

    void render(ITemplateBuilder templateBuilder);
}
