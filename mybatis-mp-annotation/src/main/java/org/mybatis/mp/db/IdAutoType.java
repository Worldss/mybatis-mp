package org.mybatis.mp.db;

public enum IdAutoType {


    /**
     * 数据库自增
     */
    AUTO,

    /**
     * 无
     */
    NONE,

    /**
     * 数据库SQL
     */
    SQL,

    /**
     * 自定义生成器
     * 需要自行实现 {@link org.apache.ibatis.executor.keygen.KeyGenerator}
     */
    GENERATOR;

}
