package db.sql.core.api.tookit;

import db.sql.api.Cmd;
import db.sql.api.SQLMode;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.DatabaseId;

public class SQLPrinter {
    public static String sql(Cmd cmd) {
        return sql(DatabaseId.MYSQL, cmd);
    }

    public static String sql(DatabaseId databaseId, Cmd cmd) {
        //创建构建SQL的上下文 数据库:MYSQL SQL模式 打印
        SqlBuilderContext sqlBuilderContext = new SqlBuilderContext(DatabaseId.MYSQL, SQLMode.PRINT);
        return cmd.sql(null, sqlBuilderContext, new StringBuilder()).toString();
    }

    public static void print(Cmd cmd) {
        print(DatabaseId.MYSQL, cmd);
    }

    public static void print(DatabaseId databaseId, Cmd cmd) {
        System.out.println(sql(databaseId, cmd));
    }
}
