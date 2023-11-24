package db.sql.api.cmd.basic;

public interface Alias<T> {

    String getAlias();

    T as(String alias);

}
