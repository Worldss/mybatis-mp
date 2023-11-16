package cn.mybatis.mp.generator.config;

import cn.mybatis.mp.core.mvc.impl.ServiceImpl;
import lombok.Getter;

@Getter
public class ServiceImplConfig {

    /**
     * 接口父类
     */
    private String superClass = ServiceImpl.class.getName();

    /**
     * 注入dao
     */
    private boolean injectDao = true;

    /**
     * 注入Mapper
     */
    private boolean injectMapper = false;

    /**
     * service实现类包名
     */
    private String packageName = "service.impl";

    /**
     * service实现后缀
     */
    private String suffix = "ServiceImpl";

    /**
     * 接口父类
     */
    public ServiceImplConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    /**
     * 注入dao
     */
    public ServiceImplConfig injectDao(boolean injectDao) {
        this.injectDao = injectDao;
        return this;
    }

    /**
     * 注入Mapper
     */
    public ServiceImplConfig injectMapper(boolean injectMapper) {
        this.injectMapper = injectMapper;
        return this;
    }

    /**
     * service实现类包名
     */
    public ServiceImplConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    /**
     * service实现后缀
     */
    public ServiceImplConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}
