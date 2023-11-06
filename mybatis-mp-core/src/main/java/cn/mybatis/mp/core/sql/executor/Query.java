package cn.mybatis.mp.core.sql.executor;

public class Query extends BaseQuery<Query> {

    private Class returnType;

    public Query() {
        super();
    }

    public static Query create() {
        return new Query();
    }

    public static Query create(Class returnType) {
        return new Query().setReturnType(returnType);
    }

    public Class getReturnType() {
        return returnType;
    }

    public Query setReturnType(Class returnType) {
        this.returnType = returnType;
        return this;
    }
}
