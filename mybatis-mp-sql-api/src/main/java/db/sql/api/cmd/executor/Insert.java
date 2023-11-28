package db.sql.api.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.cmd.struct.insert.InsertFields;
import db.sql.api.cmd.struct.insert.InsertTable;
import db.sql.api.cmd.struct.insert.InsertValues;

import java.util.List;

public interface Insert<SELF extends Insert,
        TABLE extends DATASET,
        DATASET extends Cmd,
        TABLE_FIELD extends DATASET_FILED,
        DATASET_FILED extends Cmd,
        COLUMN extends Cmd,
        V,
        INSERT_TABLE extends InsertTable<TABLE>,
        INSERT_FIELD extends InsertFields<COLUMN>,
        INSERT_VALUE extends InsertValues<V>
        >
        extends Executor<SELF, TABLE, DATASET, TABLE_FIELD, DATASET_FILED> {


    INSERT_TABLE $insert(TABLE table);


    INSERT_FIELD $field(TABLE_FIELD... fields);

    INSERT_FIELD $field(List<TABLE_FIELD> fields);

    INSERT_VALUE $values(List<V> values);


    default SELF insert(TABLE table) {
        $insert(table);
        return (SELF) this;
    }

    SELF insert(Class entity);


    default SELF field(TABLE_FIELD... fields) {
        $field(fields);
        return (SELF) this;
    }

    default SELF field(List<TABLE_FIELD> fields) {
        $field(fields);
        return (SELF) this;
    }

    <T> SELF field(Getter<T>... fields);

    SELF values(List<Object> values);

    INSERT_TABLE getInsertTable();

    INSERT_FIELD getInsertFields();

    INSERT_VALUE getInsertValues();
}
