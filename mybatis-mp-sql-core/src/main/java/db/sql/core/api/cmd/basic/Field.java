package db.sql.core.api.cmd.basic;

import db.sql.core.api.cmd.fun.FunctionInterface;

public abstract class Field<T extends Field<T>> implements Alias<T>, Value, FunctionInterface {

    protected String alias;

    public String getAlias() {
        return alias;
    }


    public T as(String alias) {
        this.alias = alias;
        return (T) this;
    }
}
