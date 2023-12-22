package db.sql.api.impl.cmd.basic;

import db.sql.api.cmd.basic.Alias;
import db.sql.api.impl.cmd.dbFun.FunctionInterface;

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
