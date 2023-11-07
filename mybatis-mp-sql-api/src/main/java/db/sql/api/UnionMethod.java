package db.sql.api;

public interface UnionMethod<SELF extends UnionMethod> {

    SELF union(Cmd cmd);

    SELF unionAll(Cmd cmd);

}
