package db.sql.api;

public interface DeleteMethod<SELF extends DeleteMethod, TABLE> {

    SELF delete(TABLE... tables);

    SELF delete(Class... entitys);
}
