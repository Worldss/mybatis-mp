package cn.mybatis.mp.generator;

import cn.mybatis.mp.generator.config.GeneratorConfig;

public class Test {

    public static void main(String[] args) {
        new FastGenerator(new GeneratorConfig(
                "casual_put",
                "jdbc:mysql://test:3306/test_db",
                "root",
                "123456")
                .basePackage("com.test")
                .includeTable("app_user")
                .columnConfig(columnConfig -> {
                    columnConfig.disableUpdateColumns("create_time");
                })
                .entityConfig(entityConfig -> {
                    entityConfig.lombok(false);
                })
                .mapperXmlConfig(mapperXmlConfig -> {
                    mapperXmlConfig.enable(true).resultMap(true).columnList(true);
                })
                .actionConfig(actionConfig -> {
                    actionConfig
                            .save(true)
                            .update(true)
                            .find(true)
                            .getById(true)
                            .deleteById(true)
                            .returnClass(Object.class.getName());
                })
        ).create();
    }
}
