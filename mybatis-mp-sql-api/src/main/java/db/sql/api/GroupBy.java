package db.sql.api;

import java.util.List;

public interface GroupBy<SELF extends GroupBy, COLUMN> {

    SELF groupBy(COLUMN column);

    
    default SELF groupBy(COLUMN... columns) {
        for (COLUMN column : columns) {
            this.groupBy(column);
        }
        return (SELF) this;
    }

    
    default SELF groupBy(List<COLUMN> columns) {
        for (COLUMN column : columns) {
            this.groupBy(column);
        }
        return (SELF) this;
    }

    List<COLUMN> getGroupByFiled();

}
