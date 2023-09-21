package org.mybatis.mp.test;

import db.sql.core.cmd.Table;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import org.mybatis.mp.core.sql.executor.Query;
import org.mybatis.mp.test.commons.DataSourceFactory;
import org.mybatis.mp.test.entity.Achievement;
import org.mybatis.mp.test.entity.Student;
import org.mybatis.mp.test.mapper.AchievementMapper;
import org.mybatis.mp.test.mapper.StudentMapper;
import org.mybatis.mp.test.vo.StudentAchievementVo;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class DataInitializer {

    private static SqlSessionFactory sqlSessionFactory;

    public static void init() {
        DataSource dataSource = DataSourceFactory.getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("Production", transactionFactory, dataSource);
        Configuration configuration = new MybatisConfiguration(environment);
        configuration.setCacheEnabled(false);
        //configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        configuration.addMapper(StudentMapper.class);
        configuration.addMapper(AchievementMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    public static void main(String[] args) {

        init();
        SqlSession session = sqlSessionFactory.openSession(true);
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        AchievementMapper achievementMapper = session.getMapper(AchievementMapper.class);

//        Student student = new Student();
//        //student.setId(11);
//        student.setName("哈哈");
//        student.setExcellent(true);
//        student.setCreateTime(LocalDateTime.now());
//        studentMapper.save(student);
//
//        student.setName("嘿嘿");
//        studentMapper.update(student);
//
//
//        System.out.println(studentMapper.getById(1));
//
//
//        student.setId(null);
//        student.setName("哈哈2");
//        studentMapper.save(student);
//        System.out.println(student);
//
//
//        student = studentMapper.getById(1);
//        System.out.println(student);
//        List<Student> list = studentMapper.all();
//        System.out.println(list);
//
//        System.out.println(studentMapper.getById2(1));
//
//        System.out.println(studentMapper.list());

        Achievement getOne111 = achievementMapper.getOneWithCmdQuery(new Query(Achievement.class) {{
            Table table = $.table("achievement");
            select($.all(table));
            from(table);
            where($.eq($.field(table, "id"), $.value("1")));
        }});

        System.out.println("<><><><><><>getOne11<>>"+getOne111);

        for(int i=0;i<20;i++){
            Achievement achievement = new Achievement();
            achievement.setScore(new BigDecimal(i+"9.99"));
            achievement.setStudent_id(1);
            achievement.setCreateTime(new Date());
            achievementMapper.save(achievement);
        }





        List<StudentAchievementVo> list2 = achievementMapper.list();
        System.out.println(list2);

        List<Achievement> list3 = achievementMapper.selectWithCmdQuery(new Query<Achievement>(Achievement.class) {{

            Table table = $.table("achievement");
            select($.all(table));
            from(table);
            where($.eq($.field(table, Achievement::getStudent_id), $.value("1")));
            limit(1);
        }});

        System.out.println("<><><><><><list3><>>"+list3);

//        List<Achievement> list4 = achievementMapper.selectWithCmdQuery(new Query(Achievement.class) {{
//            Table table = $.table("achievement");
//            select($.all(table));
//            from(table);
//            where($.eq($.field(table, "id"), $.value("1112")));
//            limit(10);
//        }});
//
//        System.out.println("<><><>list4<><><><>>"+list4);
//
//        Achievement getOne = achievementMapper.getOneWithCmdQuery(new Query(Achievement.class) {{
//            Table table = $.table("achievement");
//            select($.all(table));
//            from(table);
//            where($.eq($.field(table, "id"), $.value("1")));
//        }});
//
//        System.out.println("<><><><><><>getOne<>>"+getOne);
//
//        Achievement getOne2 = achievementMapper.getOneWithCmdQuery(new Query(Achievement.class) {{
//            Table table = $.table("achievement");
//            select($.all(table));
//            from(table);
//            where($.eq($.field(table, "id"), $.value("11133111")));
//        }});
//
//        System.out.println("<><><><><>getOne2<><>>"+getOne2);
//
//
//        Achievement getOne3= achievementMapper.getById(1111);
//        System.out.println("<><><><><>getOne3<><>>"+getOne3);
        //session.close();
    }

}
