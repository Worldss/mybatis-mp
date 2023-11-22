package db.sql.test;

import db.sql.api.Cmd;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.tookit.SQLPrinter;
import junit.framework.Assert;

public class BaseTest {
    protected Table userTable() {
        return new Table("user");
    }

    protected Table roleTable() {
        return new Table("role");
    }

    private String trim(String sql) {
        return sql.replaceAll("  ", " ")
                .replaceAll(" ,", ",")
                .replaceAll(", ", ",")
                .replaceAll(" =", "=")
                .replaceAll("= ", "=")


                .replaceAll("\\( ", "(")

                .replaceAll(" \\)", ")")

                .replaceAll("> ", ">")
                .replaceAll(" >", ">")
                .replaceAll("< ", "<")
                .replaceAll(" <", "<")

                .toLowerCase().trim();
    }

    public void check(String message, String targetSql, Cmd cmd) {
        String sql1 = trim(targetSql);
        String sql2 = trim(SQLPrinter.sql(cmd));
        System.out.println("sql1:  " + sql1);
        System.out.println("sql2:  " + sql2);
        Assert.assertEquals(message, sql1, sql2);
    }
}
