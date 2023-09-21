package org.mybatis.mp.test.commons;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
public class DataSourceFactory {

    private static int i = 0;
    public static DataSource getDataSource() {

        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setName("db" + (i++))
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .build();
        return dataSource;
    }
}
