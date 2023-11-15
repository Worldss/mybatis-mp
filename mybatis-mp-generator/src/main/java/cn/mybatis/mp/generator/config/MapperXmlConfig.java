package cn.mybatis.mp.generator.config;

import lombok.Getter;

@Getter
public class MapperXmlConfig {

    /**
     * 是否启用
     */
    private boolean enable = false;

    /**
     * 生成resultMap
     */
    private boolean resultMap = false;

    /**
     * 生成查询列
     */
    private boolean columnList = false;

    /**
     * 实体类包名
     */
    private String packageName = "mappers";

    /**
     * mapper后缀
     */
    private String suffix = "";

    /**
     * 设置是否启用
     */
    public MapperXmlConfig enable(boolean enable) {
        this.enable = enable;
        return this;
    }

    /**
     * resultMap
     */
    public MapperXmlConfig resultMap(boolean resultMap) {
        this.resultMap = resultMap;
        return this;
    }

    /**
     * baseResultMap
     */
    public MapperXmlConfig columnList(boolean columnList) {
        this.columnList = columnList;
        return this;
    }

    public MapperXmlConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public MapperXmlConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}
