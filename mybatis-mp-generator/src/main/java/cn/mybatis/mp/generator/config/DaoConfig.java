package cn.mybatis.mp.generator.config;

import cn.mybatis.mp.core.mvc.Dao;
import lombok.Getter;

@Getter
public class DaoConfig {

    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * 启用泛型
     */
    private boolean generic =true;

    /**
     * 实体类包名
     */
    private String packageName = "dao";

    /**
     * mapper后缀
     */
    private String suffix = "Dao";

    /**
     * 接口父类
     */
    private String superClass = Dao.class.getName();

    /**
     * 设置是否启用
     */
    public DaoConfig enable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public DaoConfig generic(boolean generic) {
        this.generic = generic;
        return this;
    }

    public DaoConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    public DaoConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public DaoConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}
