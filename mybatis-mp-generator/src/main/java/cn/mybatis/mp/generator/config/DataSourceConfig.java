package cn.mybatis.mp.generator.config;


import db.sql.api.DbType;
import lombok.Getter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Getter
public class DataSourceConfig {

    private final String schema;

    private final DbType dbType;

    private DataSource dataSource;

    private String url;

    private String username;

    private String password;

    public DataSourceConfig(String schema, DataSource dataSource, DbType dbType) {
        this.schema = schema;
        this.dataSource = dataSource;
        this.dbType = dbType;
    }

    public DataSourceConfig(String schema, String url, String username, String password) {
        this.schema = schema;
        this.dbType = getDbType(url);
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() {
        try {
            if (this.dataSource != null) {
                return this.getDataSource().getConnection();
            }else{
                return getConnection(this.url,this.username,this.password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(String url, String username, String password) {
        Properties properties = new Properties();
        properties.put("user", username);
        properties.put("password", password);
        DbType dbType = getDbType(url);
        addAdditionalJdbcProperties(properties, dbType);
        try {
            return DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static DbType getDbType(String jdbcUrl) {
        if (jdbcUrl.contains(":mysql:") || jdbcUrl.contains(":cobar:")) {
            return DbType.MYSQL;
        } else if (jdbcUrl.contains(":oracle:")) {
            return DbType.ORACLE;
        } else if (jdbcUrl.contains(":postgresql:")) {
            return DbType.PGSQL;
        } else if (jdbcUrl.contains(":sqlserver:")) {
            return DbType.SQL_SERVER;
        } else {
            throw new RuntimeException("Unrecognized database type");
        }
    }

    private static void addAdditionalJdbcProperties(Properties properties, DbType dbType) {
        switch (dbType) {
            case MYSQL:
                properties.put("remarks", "true");
                properties.put("useInformationSchema", "true");
                break;
            case ORACLE:
                properties.put("remarks", "true");
                properties.put("remarksReporting", "true");
                break;
        }
    }
}
