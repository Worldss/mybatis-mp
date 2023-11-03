package db.sql.api.executor;

import db.sql.api.Getter;
import db.sql.api.InsertFields;
import db.sql.api.InsertTable;
import db.sql.api.InsertValues;

import java.util.List;

public interface Insert<SELF extends Insert, TABLE, COLUMN, V, INSERT_TABLE extends InsertTable<TABLE>, INSERT_FIELD extends InsertFields<COLUMN>, INSERT_VALUE extends InsertValues<V>> {

    @SuppressWarnings("unchecked")
    INSERT_TABLE $insert(TABLE table);

    @SuppressWarnings("unchecked")
    INSERT_FIELD $field(COLUMN... fields);

    INSERT_VALUE $values(List<V> values);

    @SuppressWarnings("unchecked")
    default SELF insert(TABLE table) {
        $insert(table);
        return (SELF) this;
    }

    SELF insert(Class entity);

    @SuppressWarnings("unchecked")
    default SELF field(COLUMN... fields) {
        $field(fields);
        return (SELF) this;
    }

    @SuppressWarnings("unchecked")
    <T> SELF field(Getter<T>... fields);

    SELF values(List<Object> values);

    INSERT_TABLE getInsertTable();

    INSERT_FIELD getInsertFields();

    INSERT_VALUE getInsertValues();
}
