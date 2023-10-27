package db.sql.api;

public interface Join<SELF extends Join, TABLE, ON> {

    TABLE getMainTable();

    TABLE getSecondTable();

    JoinMode getMode();

    ON getOn();

}
