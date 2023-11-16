package cn.mybatis.mp.generator;

import cn.mybatis.mp.generator.config.GeneratorConfig;

public class Test {

    public static void main(String[] args) {
        new FastGenerator(new GeneratorConfig(
                "jdbc:mysql://test.qushiwan.cn:3306/casual_put",
                "root",
                "123456")
                .basePackage("com.test")
                .columnConfig(columnConfig -> {
                    columnConfig.disableUpdateColumns("create_time");
                })
                .entityConfig(entityConfig -> {
                    entityConfig.lombok(false);
                })
                .mapperXmlConfig(mapperXmlConfig -> {
                    mapperXmlConfig.enable(true).resultMap(true).columnList(true);
                })
                .serviceImplConfig(serviceImplConfig -> {
                    serviceImplConfig.injectMapper(true);
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
