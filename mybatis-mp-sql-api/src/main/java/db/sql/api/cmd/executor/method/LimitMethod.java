package db.sql.api.cmd.executor.method;

public interface LimitMethod<SELF> {

    SELF limit(int limit);

    SELF limit(int offset, int limit);
}
