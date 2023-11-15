package cn.mybatis.mp.demo.generator;

import cn.mybatis.mp.generator.FastGenerator;
import cn.mybatis.mp.generator.config.GeneratorConfig;
import db.sql.api.DbType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

public class DemoFastGenerator {

    public static void main(String[] args) {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setName("test_db")
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .build();

        new FastGenerator(new GeneratorConfig(
                "",
                dataSource,
                DbType.H2)
                .baseFilePath(System.getProperty("user.dir") +"/demo-generate")
                .basePackage("cn.mybatis.mp.spring.boot.demo")
                .includeTable("sys_user","sys_role")
                .columnConfig(columnConfig -> {
                    columnConfig.disableUpdateColumns("create_time");
                })
                .entityConfig(entityConfig -> {
                    entityConfig.lombok(true);
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
