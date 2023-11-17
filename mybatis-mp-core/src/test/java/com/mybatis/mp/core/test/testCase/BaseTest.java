package com.mybatis.mp.core.test.testCase;

import cn.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import com.mybatis.mp.core.test.mapper.IdTestMapper;
import com.mybatis.mp.core.test.mapper.SysRoleMapper;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.mapper.SysUserScoreMapper;
import db.sql.api.cmd.Cmd;
import db.sql.core.api.tookit.SQLPrinter;
import junit.framework.Assert;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class BaseTest {

    protected SqlSessionFactory sqlSessionFactory;

    protected EmbeddedDatabase dataSource;


    @BeforeEach
    public void init() {

        dataSource = new EmbeddedDatabaseBuilder()
                .setName("test_db")
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .build();

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("Test", transactionFactory, dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration(environment);
        configuration.addMapper(SysUserMapper.class);
        configuration.addMapper(SysRoleMapper.class);
        configuration.addMapper(SysUserScoreMapper.class);
        configuration.addMapper(IdTestMapper.class);
        configuration.setLogImpl(StdOutImpl.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }


    @AfterEach
    public void close() {
        dataSource.shutdown();
    }

    public void check(String message, String targetSql, Cmd cmd) {
        this.check(message, targetSql, SQLPrinter.sql(cmd));
    }

    public void check(String message, String targetSql, String sql) {
        String sql1 = trim(targetSql);
        String sql2 = trim(sql);
        System.out.println("sql1:  " + sql1);
        System.out.println("sql2:  " + sql2);
        Assert.assertEquals(message, sql1, sql2);
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

}
