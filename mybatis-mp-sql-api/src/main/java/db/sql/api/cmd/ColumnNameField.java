package db.sql.api.cmd;

public class ColumnNameField implements ColumnField {

    private final String columnName;

    public ColumnNameField(String columnName) {
        this.columnName = columnName;
    }

    public static ColumnNameField create(String columnName) {
        return new ColumnNameField(columnName);
    }

    public String getColumnName() {
        return columnName;
    }
}
