package db.sql.api;

public interface Having<SELF extends Having> {

    <T> SELF and(Condition condition);

    <T> SELF or(Condition condition);
}
