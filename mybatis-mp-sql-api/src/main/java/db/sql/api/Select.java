package db.sql.api;

import java.util.List;

public interface Select<SELF extends Select, COLUMN> {

    SELF select(COLUMN column);

    @SuppressWarnings("unchecked")
    SELF select(COLUMN... columns);

    @SuppressWarnings("unchecked")
    SELF select(List<COLUMN> columns);

    List<COLUMN> getSelectFiled();

}
