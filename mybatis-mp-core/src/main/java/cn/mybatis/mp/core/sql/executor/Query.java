package cn.mybatis.mp.core.sql.executor;

import cn.mybatis.mp.core.db.reflect.ForeignInfo;
import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.JoinMode;
import db.sql.core.api.cmd.On;
import db.sql.core.api.cmd.TableField;

import java.util.function.Consumer;
import java.util.function.Function;

public class Query extends BaseQuery<Query> {

    private Class returnType;

    public Query() {
        super();
    }

    public Class getReturnType() {
        return returnType;
    }

    public Query setReturnType(Class returnType) {
        this.returnType = returnType;
        return this;
    }
}
