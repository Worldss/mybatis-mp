package cn.mybatis.mp.core.mybatis.provider;


import db.sql.api.DbType;
import db.sql.api.SQLMode;
import db.sql.api.SqlBuilderContext;

import java.util.ArrayList;
import java.util.List;

public class MybatisSqlBuilderContext extends SqlBuilderContext {

    public MybatisSqlBuilderContext(DbType dbType, SQLMode sqlMode) {
        super(dbType, sqlMode);
    }

    private final List<Object> paramList = new ArrayList<>();

    @Override
    public String addParam(Object value) {
        paramList.add(value);
        return "?";
    }

    public Object[] getParams() {
        return paramList.stream().toArray();
    }
}
