package cn.mybatis.mp.generator.config;

import cn.mybatis.mp.core.mvc.impl.DaoImpl;
import cn.mybatis.mp.core.mvc.impl.ServiceImpl;
import lombok.Getter;

@Getter
public class ServiceImplConfig {

    /**
     * 注入dao
     */
    private boolean injectDao = true;

    /**
     * 实体类包名
     */
    private String packageName = "service.impl";

    /**
     * mapper后缀
     */
    private String suffix = "ServiceImpl";

    /**
     * 接口父类
     */
    private String superClass = ServiceImpl.class.getName();


    public ServiceImplConfig injectDao(boolean injectDao) {
        this.injectDao = injectDao;
        return this;
    }

    public ServiceImplConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    public ServiceImplConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public ServiceImplConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}
