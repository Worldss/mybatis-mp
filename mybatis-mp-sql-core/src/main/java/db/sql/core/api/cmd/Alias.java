package db.sql.core.api.cmd;

public interface Alias<T> {

    String getAlias();

    T as(String alias);

}
