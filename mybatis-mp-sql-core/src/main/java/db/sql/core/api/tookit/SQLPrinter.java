package db.sql.core.api.tookit;

import db.sql.api.DbType;
import db.sql.api.SQLMode;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.Cmd;

public class SQLPrinter {
    public static String sql(Cmd cmd) {
        return sql(DbType.MYSQL, cmd);
    }

    public static String sql(DbType dbType, Cmd cmd) {
        //创建构建SQL的上下文 数据库:MYSQL SQL模式 打印
        SqlBuilderContext sqlBuilderContext = new SqlBuilderContext(DbType.MYSQL, SQLMode.PRINT);
        return cmd.sql(null, sqlBuilderContext, new StringBuilder()).toString();
    }

    public static void print(Cmd cmd) {
        print(DbType.MYSQL, cmd);
    }

    public static void print(DbType dbType, Cmd cmd) {
        System.out.println(sql(dbType, cmd));
    }
}
