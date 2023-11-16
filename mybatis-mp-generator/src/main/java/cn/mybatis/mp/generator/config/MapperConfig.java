package cn.mybatis.mp.generator.config;

import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import lombok.Getter;

@Getter
public class MapperConfig {

    /**
     * Mapper接口父类
     */
    private String superClass = MybatisMapper.class.getName();

    /**
     * 是否使用 @Mapper
     */
    private boolean mapperAnnotation = true;

    /**
     * mapper接口包名
     */
    private String packageName = "mapper";

    /**
     * mapper接口后缀
     */
    private String suffix = "Mapper";


    /**
     * Mapper接口父类
     */
    public MapperConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    /**
     * 是否使用 @Mapper
     */
    public MapperConfig mapperAnnotation(boolean mapperAnnotation) {
        this.mapperAnnotation = mapperAnnotation;
        return this;
    }

    /**
     * mapper接口包名
     */
    public MapperConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    /**
     * mapper接口后缀
     */
    public MapperConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}
