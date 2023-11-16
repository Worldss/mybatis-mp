package db.sql.api.cmd.executor.method;

public interface DeleteMethod<SELF extends DeleteMethod, TABLE> {


    SELF delete(TABLE... tables);

    SELF delete(Class<?>... entities);
}
