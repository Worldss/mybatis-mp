package cn.mybatis.mp.core.incrementer;

public class IdentifierGeneratorType {

    /**
     * 基于java uuid获取
     */
    public static final String UUID = "UUID";

    /**
     * 基于雪花算法
     */
    public static final String mpNextId = "mpNextId";

    /**
     * 默认生成器
     */
    public static final String DEFAULT = "DEFAULT";
}
