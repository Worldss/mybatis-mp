package cn.mybatis.mp.generator.config;

import cn.mybatis.mp.core.mvc.Service;
import lombok.Getter;

@Getter
public class ServiceConfig {

    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * 启用泛型
     */
    private boolean generic =false;

    /**
     * 实体类包名
     */
    private String packageName = "service";

    /**
     * mapper后缀
     */
    private String suffix = "Service";

    /**
     * 接口父类
     */
    private String superClass = Service.class.getName();

    /**
     * 设置是否启用
     */
    public ServiceConfig enable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public ServiceConfig generic(boolean generic) {
        this.generic = generic;
        return this;
    }

    public ServiceConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    public ServiceConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public ServiceConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}
