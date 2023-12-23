package db.sql.api.cmd;

public class ColumnField implements IColumnField {

    private final String columnName;

    public ColumnField(String columnName) {
        this.columnName = columnName;
    }

    public static ColumnField create(String columnName) {
        return new ColumnField(columnName);
    }

    public String getColumnName() {
        return columnName;
    }
}
