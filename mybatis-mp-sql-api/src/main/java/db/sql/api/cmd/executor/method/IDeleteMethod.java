package db.sql.api.cmd.executor.method;

public interface IDeleteMethod<SELF extends IDeleteMethod, TABLE> {


    SELF delete(TABLE... tables);

    SELF delete(Class<?>... entities);
}
