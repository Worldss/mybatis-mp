package db.sql.api.cmd;

public enum JoinMode {

    LEFT(" LEFT JOIN "), RIGHT(" RIGHT JOIN "), INNER(" INNER JOIN "), FULL(" FULL OUTER JOIN ");

    private final String sql;

    JoinMode(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

}
