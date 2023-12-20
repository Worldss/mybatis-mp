package cn.mybatis.mp.core.mybatis.configuration;

import cn.mybatis.mp.core.mybatis.mapper.context.SQLCmdQueryContext;
import cn.mybatis.mp.core.sql.executor.BaseQuery;

public class MapKeySQLCmdQueryContext extends SQLCmdQueryContext {

    private String key;

    public MapKeySQLCmdQueryContext(String key, BaseQuery execution, boolean optimize) {
        super(execution, optimize);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
