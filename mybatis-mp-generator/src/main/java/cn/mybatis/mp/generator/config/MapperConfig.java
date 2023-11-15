package cn.mybatis.mp.generator.config;

import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import lombok.Getter;

@Getter
public class MapperConfig {

    /**
     * 是否使用 @Mapper
     */
    private boolean mapperAnnotation = true;

    /**
     * 实体类包名
     */
    private String packageName = "mapper";

    /**
     * mapper后缀
     */
    private String suffix = "Mapper";

    /**
     * 接口父类
     */
    private String superClass = MybatisMapper.class.getName();

    /**
     * 是否使用 @Mapper
     */
    public MapperConfig mapperAnnotation(boolean mapperAnnotation) {
        this.mapperAnnotation = mapperAnnotation;
        return this;
    }

    public MapperConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    public MapperConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public MapperConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }


}
