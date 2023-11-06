package cn.mybatis.mp.spring.boot.demo;

import cn.mybatis.mp.core.mybatis.configuration.MybatisMpConfig;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Configurable
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        MybatisMpConfig.setTableUnderline(true); //数据库表是否下划线规则 默认 true
        MybatisMpConfig.setColumnUnderline(true); ///数据库列是否下划线规则 默认 true
        SpringApplication.run(DemoApplication.class, args);
    }
}
