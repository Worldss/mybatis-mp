package db.sql.api;

public interface DeleteMethod<SELF extends DeleteMethod, TABLE> {

    @SuppressWarnings("unchecked")
    SELF delete(TABLE... tables);

    SELF delete(Class<?>... entities);
}
