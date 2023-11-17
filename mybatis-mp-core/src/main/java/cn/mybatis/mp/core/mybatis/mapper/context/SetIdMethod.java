package cn.mybatis.mp.core.mybatis.mapper.context;

import cn.mybatis.mp.core.db.reflect.TableFieldInfo;

public interface SetIdMethod {
    /**
     * 设置插入ID
     *
     * @param id
     */
    void setId(Object id);
}
