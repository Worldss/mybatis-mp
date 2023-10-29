package cn.mybatis.mp.test;


import cn.mybatis.mp.core.mybatis.configuration.MybatisConfiguration;
import cn.mybatis.mp.core.mybatis.configuration.MybatisMpConfig;
import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.core.sql.executor.Insert;
import cn.mybatis.mp.core.sql.executor.Query;
import cn.mybatis.mp.core.sql.executor.Update;
import cn.mybatis.mp.test.commons.DataSourceFactory;
import cn.mybatis.mp.test.entity.Achievement;
import cn.mybatis.mp.test.entity.Student;
import cn.mybatis.mp.test.mapper.AchievementMybatisMapper;
import cn.mybatis.mp.test.mapper.StudentMapper;
import cn.mybatis.mp.test.vo.StudentAchievementVo;
import db.sql.core.api.cmd.Table;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
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
        configuration.addMapper(AchievementMybatisMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);


    }

    public static void main(String[] args) {
        MybatisMpConfig.setTableUnderline(true);
        MybatisMpConfig.setColumnUnderline(true);
        init();
        SqlSession session = sqlSessionFactory.openSession(true);
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        AchievementMybatisMapper achievementMapper = session.getMapper(AchievementMybatisMapper.class);

        Student student = new Student();
        //student.setId(11);
        student.setName("哈哈");
        student.setExcellent(true);
        student.setCreateTime(LocalDateTime.now());
        studentMapper.save(student);

        studentMapper.save(new Insert().insert(Student.class).field(
                Student::getName,
                Student::getExcellent,
                Student::getCreateTime
        ).values(Arrays.asList("哈哈", true, LocalDateTime.now())));

        System.out.println(student);

        student.setName("嘿嘿");
        studentMapper.update(student);


        studentMapper.update(new Update()
                .update(Student.class)
                .set(Student::getName, "嘿嘿")
                .eq(Student::getId, 1)
        );


        System.out.println(studentMapper.getById(1));


        student.setId(null);
        student.setName("哈哈2");
        studentMapper.save(student);
        System.out.println(student);


        student = studentMapper.getById(1);
        System.out.println(student);
        List<Student> list = studentMapper.all();
        System.out.println(list);


        Student stu = studentMapper.getById(1);
        Student stu2 = studentMapper.get(new Query().from(Student.class).eq(Student::getId, 1));
        Student stu3 = studentMapper.get(new Query().select(Student::getName, Student::getCreateTime).from(Student.class).eq(Student::getId, 1));


        Achievement getOne111 = achievementMapper.get(new Query().
                select(Achievement::getId).
                from(Achievement.class).
                eq(Achievement::getId, 1)
        );

        System.out.println("<><><><><><>getOne11<>>" + getOne111);

        for (int i = 0; i < 20; i++) {
            Achievement achievement = new Achievement();
            achievement.setScore(new BigDecimal(i + "9.99"));
            achievement.setStudentId(1);
            achievement.setCreateTime(new Date());
            achievementMapper.save(achievement);
            if (i == 0) {
                Achievement updateAchievement = new Achievement();
                updateAchievement.setId(achievement.getId());
                updateAchievement.setScore(new BigDecimal("10"));
                achievementMapper.update(updateAchievement);
            }
        }

        Achievement getOne222 = achievementMapper.get(new Query() {{
            Table table = $.table("achievement");
            select($.all(table));
            from(table);
            eq($.field(table, "id"), $.value("1"));
        }});

        System.out.println("<><><><><><>getOne222<>>" + getOne222);


        Pager<Achievement> pager = new Pager<>();
        pager.setSize(3);
        pager = achievementMapper.paging(new Query() {{
            Table table = $.table(Achievement.class, 1);
            select($.all(table));
            from(table);
            //eq($.field(table, Achievement::getStudent_id), $.value("1"));
            eq(Achievement::getStudentId, $.value(1));
            //limit(1);
        }}, pager);

        System.out.println("<><><><><><pager><>>" + pager);

        achievementMapper.deleteById(1);

        List<Achievement> list4 = achievementMapper.list(new Query() {{
            Table table = $.table("achievement");
            select($.all(table));
            from(table);
            eq($.field(table, "id"), $.value("1112"));
            limit(10);
        }});

        System.out.println("<><><>list4<><><><>>" + list4);

        Achievement getOne = achievementMapper.get(new Query() {{
            Table table = $.table("achievement");
            select($.all(table));
            from(table);
            eq($.field(table, "id"), $.value("1"));
        }});

        System.out.println("<><><><><><>getOne<>>" + getOne);

        Achievement getOne2 = achievementMapper.get(new Query() {{
            Table table = $.table("achievement");
            select($.all(table));
            from(table);
            eq($.field(table, "id"), $.value("11133111"));
        }});

        System.out.println("<><><><><>getOne2<><>>" + getOne2);


        Achievement getOne3 = achievementMapper.getById(1111);
        System.out.println("<><><><><>getOne3<><>>" + getOne3);

        List<Achievement> achievementList = achievementMapper.list(new Query()
                .select(Achievement.class)
                .select(Student::getName)
                .from(Achievement.class)
                .join(Achievement.class, Student.class)
                .eq(Achievement::getId, 1)
        );

        List<Achievement> achievementList2 = achievementMapper.list(new Query()
                .select(Achievement.class)
                .select(Student::getName)
                .from(Achievement.class)
                .join(Achievement.class, Student.class, on -> on.eq(Achievement::getStudentId, Student::getId))
                .eq(Achievement::getId, 1)
        );


        List<Achievement> joinResultList = achievementMapper.list(new Query()
                //.select(Achievement.class)
                .select(Achievement::getId, column -> column.max().as("id"))
                .from(Achievement.class, table -> table.as("a"))
                .join(Achievement.class, Student.class)
                .eq(Achievement::getId, 123).andNested(conditionChain -> conditionChain.eq(Achievement::getId, 123).or().eq(Achievement::getId, 124))
                .groupBy(Achievement::getId)
                .having(having -> having.and($ -> $.field(Achievement::getId, 1).count().gt(1)))
                .orderBy(Achievement::getId)
        );

        List<StudentAchievementVo> joinResultList2 = achievementMapper.list(new Query()
                .select(Student.class, Achievement.class)
                .from(Student.class)
                .join(Student.class, Achievement.class)
                .setReturnType(StudentAchievementVo.class));
        System.out.println("<><><><><>joinResultList<><>>" + joinResultList2);

        session.close();
    }

}
