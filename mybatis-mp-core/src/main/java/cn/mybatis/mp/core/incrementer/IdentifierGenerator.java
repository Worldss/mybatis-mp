package cn.mybatis.mp.core.incrementer;

public interface IdentifierGenerator<T> {


    /**
     * 生成Id
     *
     * @param entity 实体
     * @return id
     */
    T nextId(Class<?> entity);
}
