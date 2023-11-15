package cn.mybatis.mp.generator.config;

import cn.mybatis.mp.core.mvc.Dao;
import cn.mybatis.mp.core.mvc.impl.DaoImpl;
import lombok.Getter;

@Getter
public class DaoImplConfig {

    /**
     * 实体类包名
     */
    private String packageName = "dao.impl";

    /**
     * mapper后缀
     */
    private String suffix = "DaoImpl";

    /**
     * 接口父类
     */
    private String superClass = DaoImpl.class.getName();

    public DaoImplConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    public DaoImplConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public DaoImplConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}
