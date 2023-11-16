package db.sql.core.api.cmd.basic;

public interface Alias<T> {

    String getAlias();

    T as(String alias);

}
