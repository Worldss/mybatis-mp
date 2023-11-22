package db.sql.api.impl.cmd.basic;

public interface Alias<T> {

    String getAlias();

    T as(String alias);

}
