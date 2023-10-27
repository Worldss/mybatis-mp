package db.sql.api;

import java.util.List;

public interface Select<SELF extends Select, COLUMN> {

    SELF select(COLUMN column);

    SELF select(COLUMN... columns);

    SELF select(List<COLUMN> columns);

    List<COLUMN> getSelectFiled();

}
