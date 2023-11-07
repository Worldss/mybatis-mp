# mybatis-mp ，诚邀各位进行参与补充
<p align="center">
    <a target="_blank" href="https://search.maven.org/search?q=mybatis-mp%20mybatis-mp">
        <img src="https://img.shields.io/maven-central/v/cn.mybatis-mp/mybatis-mp?label=Maven%20Central" alt="Maven" />
    </a>
    <a target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0.txt">
		<img src="https://img.shields.io/:license-Apache2-blue.svg" alt="Apache 2" />
	</a>
    <a target="_blank" href='https://gitee.com/hiaii/mybatis-mp'>
		<img src='https://gitee.com/hiaii/mybatis-mp/badge/star.svg' alt='Gitee star'/>
	</a>
</p>
## 特征

#### 1、很轻量,非常轻量
> 轻量级封装mybatis。
> 其他框架都比较深度修改了mybatis源码。

#### 2、高性能
> 对比其他mybatis框架，性能不差，接近最优。

#### 3、灵活方便
> 中高度实现ORM，查询API零学习成本。

#### 4、高可用
> 可应付90%的SQL需求。

#### 5、可靠，安全
> 没有过于复杂的设计，但是api却很丰富，足够使用！
> 其他框架或多或少设计的过于复杂，反而容易出现各种问题。

## QQ 群

群号： 917404304 ,邀请各位大神参与补充，绝对开源，大家都可以进行代码提交，审核通过会进行master分支。
![](./doc/image/qq.png)

## 快速开始
### maven 集成

```
<dependency>
    <groupId>cn.mybatis-mp</groupId>
    <artifactId>mybatis-mp-spring-boot-starter</artifactId>
    <version>1.0.6</version>
</dependency>  
```
### 数据源 配置

配置spring boot配置文件

```
spring.datasource.url=jdbc:mysql://localhost/test
spring.datasource.username=dbuser
spring.datasource.password=dbpass
```
或者 自己实例一个 DataSource 也可以

```
@Configuration(proxyBeanMethods = false)
public class DatasourceConfig {

    @Bean
    public DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setName("test_db")
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .build();
    }
}

```
### 其他配置(可不配置)
#### 数据库命名规则配置(可不配置)
```
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        MybatisMpConfig.setTableUnderline(true); //数据库表是否下划线规则 默认 true
        MybatisMpConfig.setColumnUnderline(true); ///数据库列是否下划线规则 默认 true
        SpringApplication.run(DemoApplication.class, args);
    }
}
```
#### 数据库类型配置(可不配置，默认MYSQL)
```
mybatis:
    configuration:
        databaseId: MYSQL
```
> 更多数据库支持，请查看类：db.sql.api.DbType 

> 启动springboot即可，非常简单

### 一切都就绪，启动即可

> 1.添加此依赖，无需再添加mybatis依赖

> 2.包含 mybatis、mybatis-spring、 mybatis-spring-boot-starter 所有功能（支持原有mybatis的所有功能）

> 3.更多mybatis 配置参数，参考 https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/zh/index.html

> 4.参考示例：https://gitee.com/hiaii/mybatis-mp/tree/master/mybatis-mp-spring-boot-demo

> 5.更多 mybatis 用法，参考：
> mybatis：https://mybatis.org/mybatis-3/zh/index.html
> 
> mybatis spring: https://mybatis.org/spring/zh/index.html

> 6.更多mybatis-mp 用法，参考作者编写的test case:(包含各种简单，复杂的CRUD操作案例)
> 
> https://gitee.com/hiaii/mybatis-mp/tree/master/mybatis-mp-core/src/test/java/com/mybatis/mp/core/test/testCase


# 注解
### 1. @Table
```java
@Table
public class Student {
    
}
```
### 2. @TableId
```java
@Table
public class Student {
    
    @TableId(value = IdAutoType.SQL, sql = "SELECT LAST_INSERT_ID()")
    private Integer id;
}
```
> @TableId 支持不同数据库自增或序列 以及 自定义SQL，默认是数据库自增
### 3. @TableField 数据库字段注解
```java
@Table
public class Student {
     
    @TableField(typeHandler = LocalDateTimeTypeHandler.class)
    private LocalDateTime createTime;
}
```
> @TableField 可以设置列名、typeHandler、jdbcType、是否查询、是否更新 等
### 4. @Ignore 可忽略字段（可用的实体类 VO 等字段上）
### 5. @ForeignKey 外键，配置后 JOIN时无需要再加 ON条件（自动添加）
```java
@Data
@Table
public class Achievement {

    @TableId(IdAutoType.AUTO)
    private Integer id;

    @ForeignKey(Student.class)
    private Integer studentId;

    private BigDecimal score;

    private Date createTime;
}
```
### 6. 非实体类结果映射
```java
@Data
@ToString(callSuper = true)
@ResultEntity(Student.class)
public class StudentVo {

    private Integer id;

    private String name;

    //private LocalDateTime stCreateTime;

    @ResultEntityField(target = Student.class, property = "createTime")
    private LocalDateTime st2CreateTime;
}

@Data
@ToString(callSuper = true)
@ResultEntity(Student.class)
public class StudentAchievementVo extends StudentVo {

    @ResultEntityField(target = Achievement.class, property = "id")
    private Integer achievementId;

    @NestedResultEntity(target =  Achievement.class)
    private Achievement achievement;

    @ResultField(value = "xx_score")
    private Integer score;

}
```
#### @ResultEntity 
> 用于返回类 和 实体类关联 (只能配置一个，哪个返回列多，配哪个实体类)
#### @ResultEntityField 
> 用于解决字段名字和实体类名字不一致的问题
#### @NestedResultEntity 
> 用于内嵌类的映射 和 @ResultEntity 类似
#### @NestedResultEntityField 
> 用于解决内嵌字段名字和实体类名字不一致的问题
#### @ResultField 
> 返回列的映射（用于非实体类字段，可以设置列名、typeHandler、jdbcType）
> 返回可以平级 或者 1级 2级 两层映射


# mybatis Mapper 实现
> 需要继承 MybatisMapper
```java
public interface StudentMapper extends MybatisMapper<Student> {
    
}
```

## 1.Mapper方法

### 单个查询
> getById(Serializable id) 根据ID查询实体
> 
> R get(Query query) 单个动态查询（可自定返回类型）

### 列表查询
> List<R> list(Query query) 列表动态查询（可自定返回类型）
> 
> all(Query query) 全表查询

### count查询
> count(Query query) 动态count查询

### 删除
> deleteById(Serializable id) 根据ID删除
> 
> delete(T entity) 根据实体类删除
> 
> delete(Delete delete) 动态删除

### 保存
> save(T entity) 实体类保存
> 
> save(Model entity) 实体类部分保存
> 
> save(Insert insert) 动态插入（无法返回ID）


### 修改
> update(T entity) 实体类更新
> 
> update(T entity,F... fields) 实体类更新 + 强制字段更新
> 
> update(Model entity) 实体类部分更新
> 
> update(Model entity,F... fields) 实体类部分更新 + 强制字段更新
> 
> update(Update update) 动态更新

### 分页查询
> Pager<R> paging(Query query, Pager<R> pager) 分页查询（可自定返回类型）

# CRUD 操作
### 1.1 单个查询

```agsl
     Student stu=studentMapper.getById(1);
     或
     Student stu2=studentMapper.get(new Query().from(Student.class).eq(Student::getId,1));
```
> 能用前者优先前者，后者为单个动态查询

### 1.2 选择部分列

```agsl
     Student stu3=studentMapper.get(new Query().select(Student::getName,Student::getCreateTime).from(Student.class).eq(Student::getId,1));
```
>
### 1.3 join 连表查询
#### 1.3.1 基础用法
```agsl
    List<Achievement> achievementList = achievementMapper.list(new Query()
            .select(Achievement.class)
            .select(Student::getName)
            .from(Achievement.class)
            .join(Achievement.class, Student.class,on->on.eq(Achievement::getStudentId,Student::getId))
            .eq(Achievement::getId, 1)
    );
```
> 支持各种连接：INNER JOIN ,LEFT JOIN 等等
#### 1.3.2 省力用法
>  join 可结合 @ForeignKey使用 这样无需加 ON 条件
```agsl
    List<Achievement> achievementList2 = achievementMapper.list(new Query()
            .select(Achievement.class)
            .select(Student::getName)
            .from(Achievement.class)
            .join(Achievement.class, Student.class)
            .eq(Achievement::getId, 1)
    );
```
> 注意，假如自己拼接上了 on 条件 ，则 @ForeignKey 不生效，需要自己把条件书写完成！

#### 1.3.3 相同表 join
```
    SysUser sysUser = sysUserMapper.get(new Query()
            .select(SysUser.class)
            .from(SysUser.class)
            .join(JoinMode.INNER, SysUser.class, 1, SysUser.class, 2, on -> on.eq(SysUser::getId, 1, SysUser::getRole_id, 2))
    );
```
> 注意 select(SysUser.class) 只是返回前面那个表的数据，如需返回后面那个表，则需要 结合注解@ResultField(别名)
> 
> 然后 new Query().select(SysUser::getUserName,2,c->c.as("sub_name"))，其中2 表示实体类SysUser对应的表实例的缓存层级
#### 1.3.4 不同表join
```
    List<SysUserAndRole> list = sysUserMapper.list(new Query()
            .select(SysUser.class)
            .select(SysRole.class)
            .from(SysUser.class)
            .join(SysUser.class, SysRole.class,on -> on.eq(SysUser::getRole_id, SysRole::getId))
            .setReturnType(SysUserAndRole.class)
    );
```
> SysUserAndRole 如何映射，请查看注解说明。
#### 1.3.5 join 子表
```
    List<SysUser> list = sysUserMapper.list(new Query() {{
        select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id)
                .from(SysUser.class)
                .in(SysUser::getId, new SubQuery()
                        .select(SysUser::getId)
                        .from(SysUser.class)
                        .eq(SysUser::getId, $(SysUser::getId))
                        .isNotNull(SysUser::getPassword)
                        .limit(1)
                );

    }});
```
    
### 1.4 删除

```agsl
     studentMapper.deleteById(1);
     或
     studentMapper.delete(new Delete().from(Student.class).eq(Student::getId,1));
```
> 能用前者优先前者，后者为单个动态删除
>
### 1.5 新增

```agsl
    Student student = new Student();
    //student.setId(11);
    student.setName("哈哈");
    student.setExcellent(true);
    student.setCreateTime(LocalDateTime.now());
    studentMapper.save(student);
    
    或者
    
    @Data
    public class StudentDTO implements cn.mybatis.mp.db.Model<Student> {
    
        private Integer id;
    
        private String name;
    
        private LocalDateTime createTime;
    }
    
    StudentDTO studentDTO=new StudentDTO();
    studentDTO.setName("DTO Insert");
    studentDTO.setCreateTime(LocalDateTime.now());
    studentMapper.save(studentDTO);
    
    或者
    
    studentMapper.save(new Insert().insert(Student.class).field(
            Student::getName,
            Student::getExcellent,
            Student::getCreateTime
    ).values(Arrays.asList("哈哈", true, LocalDateTime.now())));
    
```
> 能用前者优先前者，后者为动态插入（可多个）
>

### 1.6 更新

```agsl
    Student student = new Student();
    student.setName("哈哈");
    student.setExcellent(true);
    student.setCreateTime(LocalDateTime.now());
    studentMapper.update(student);
    
    或者
    
    @Data
    public class StudentDTO implements cn.mybatis.mp.db.Model<Student> {
    
        private Integer id;
    
        private String name;
    
        private LocalDateTime createTime;
    }
    
    StudentDTO studentDTO=new StudentDTO();
    studentDTO.setId(1)
    studentDTO.setName("DTO Insert");
    studentMapper.update(studentDTO);
    
    或者
    
    studentMapper.update(new Update()
            .update(Student.class)
            .set(Student::getName, "嘿嘿")
            .eq(Student::getId, 1)
    );
    
```
> 能用前者优先前者，后者为动态更新
>

### 1.7 group by
```
List<Integer> counts = sysUserMapper.list(new Query()
        .select(SysUser::getId, c -> c.count())
        .from(SysUser.class)
        .groupBy(SysUser::getRole_id)
        .setReturnType(Integer.TYPE)
);
```

### 1.8 order by
```
SysUser sysUser = sysUserMapper.get(new Query()
        .select(SysUser::getId, SysUser::getUserName,SysUser::getRole_id)
        .from(SysUser.class)
        .orderBy(false,SysUser::getRole_id,SysUser::getId)
        .limit(1)
);
```
### 1.9 group by having
```
Integer count = sysUserMapper.get(new Query()
        .select(SysUser::getRole_id, FunctionInterface::count)
        .from(SysUser.class)
        .groupBy(SysUser::getRole_id)
        .having(SysUser::getRole_id, c -> c.gt(0))
        .setReturnType(Integer.TYPE)
);
```
### 2.0 条件
#### 2.1 and or 相互切换
```
    sysUserMapper.get(new Query()
            .select(SysUser::getId)
            .from(SysUser.class)
            .eq(SysUser::getId, 2).or().eq(SysUser::getId, 1)
            .setReturnType(Integer.TYPE)
    );
```
> 调用 and(),则后续操作默认都and操作
> 
> 调用 or(),则后续操作默认都or操作
#### 2.2 大于( gt() )
```
    sysUserMapper.get(new Query()
            .select(SysUser::getId)
            .from(SysUser.class)
            .gt(SysUser::getId, 2)
            .setReturnType(Integer.TYPE)
    );
```    
#### 2.3 大于等于( gte() )
```
    sysUserMapper.get(new Query()
            .select(SysUser::getId)
            .from(SysUser.class)
            .gte(SysUser::getId, 2)
            .setReturnType(Integer.TYPE)
    );
```   
#### 2.4 小于( lt() )
```
    sysUserMapper.get(new Query()
            .select(SysUser::getId)
            .from(SysUser.class)
            .lt(SysUser::getId, 2)
            .setReturnType(Integer.TYPE)
    );
```    
#### 2.5 小于等于( lte() )
```
    sysUserMapper.get(new Query()
            .select(SysUser::getId)
            .from(SysUser.class)
            .lte(SysUser::getId, 2)
            .setReturnType(Integer.TYPE)
    );
```   

#### 2.6 等于( eq() )
```
    sysUserMapper.get(new Query()
            .select(SysUser::getId)
            .from(SysUser.class)
            .lt(SysUser::getId, 2)
            .setReturnType(Integer.TYPE)
    );
```    
#### 2.7 不等于( ne() )
```
    sysUserMapper.get(new Query()
            .select(SysUser::getId)
            .from(SysUser.class)
            .lte(SysUser::getId, 2)
            .setReturnType(Integer.TYPE)
    );
```  

#### 2.8 is NULL
```
    sysUserMapper.get(new Query()
            .select(SysUser::getId)
            .from(SysUser.class)
            .isNull(SysUser::getId)
            .setReturnType(Integer.TYPE)
    );
```    
#### 2.9 is NOT NULL
```
    sysUserMapper.get(new Query()
            .select(SysUser::getId)
            .from(SysUser.class)
            .isNotNull(SysUser::getId)
            .setReturnType(Integer.TYPE)
    );
```  
#### 3.0 in
```
    List<Integer> list = sysUserMapper.list(new Query().
            select(SysUser::getId).
            from(SysUser.class).
            in(SysUser::getId, 1, 2).
            setReturnType(Integer.TYPE)
    );
```
#### 3.1 like
```
     SysUser sysUser = sysUserMapper.get(new Query() {{
        select(SysUser::getId, SysUser::getPassword, SysUser::getRole_id);
        from(SysUser.class);
        like(SysUser::getUserName, "test1");
    }});
```
#### 3.2 left like
```
     SysUser sysUser = sysUserMapper.get(new Query().
            select(SysUser::getId, SysUser::getPassword, SysUser::getRole_id).
            from(SysUser.class).
            like(SysUser::getUserName, "test1", LikeMode.LEFT)

    );
```
#### 3.3 right like
```
    Integer count = sysUserMapper.get(new Query().
            select(SysUser::getId, c -> c.count()).
            from(SysUser.class).
            like(SysUser::getUserName, "test", LikeMode.RIGHT).
            setReturnType(Integer.TYPE)
    );
```

#### 3.4 not like
```
     SysUser sysUser = sysUserMapper.get(new Query() {{
        select(SysUser::getId, SysUser::getPassword, SysUser::getRole_id);
        from(SysUser.class);
        notLike(SysUser::getUserName, "test1");
    }});
```
#### 3.5 not left like
```
     SysUser sysUser = sysUserMapper.get(new Query().
            select(SysUser::getId, SysUser::getPassword, SysUser::getRole_id).
            from(SysUser.class).
            notLike(SysUser::getUserName, "test1", LikeMode.LEFT)

    );
```
#### 3.6 not right like
```
    Integer count = sysUserMapper.get(new Query().
            select(SysUser::getId, c -> c.count()).
            from(SysUser.class).
            notLike(SysUser::getUserName, "test", LikeMode.RIGHT).
            setReturnType(Integer.TYPE)
    );
```
#### 3.7 between
```
    List<Integer> list = sysUserMapper.list(new Query().
            select(SysUser::getId).
            from(SysUser.class).
            between(SysUser::getId, 1, 2).
            setReturnType(Integer.TYPE)
    );
```
#### 3.8 not between
```
    List<Integer> list = sysUserMapper.list(new Query().
            select(SysUser::getId).
            from(SysUser.class).
            between(SysUser::getId, 1, 3).
            notBetween(SysUser::getId, 1, 2).
            setReturnType(Integer.TYPE)
    );
```
#### 3.9 exists
```
    List<SysUser> list = sysUserMapper.list(new Query() {{
        select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id)
                .from(SysUser.class)
                .exists(new SubQuery()
                        .select1()
                        .from(SysUser.class)
                        .eq(SysUser::getId, $(SysUser::getId))
                        .isNotNull(SysUser::getPassword)
                        .limit(1)
                );

    }});
```

# 函数操作
### 1.1 聚合函数（min,count,max,avg）

```
    Integer count = sysUserMapper.get(new Query().
            select(SysUser::getId, c -> c.count()).
            from(SysUser.class).
            setReturnType(Integer.TYPE)
    );
```
```
    select(SysUser::getId, c -> c.min())
    select(SysUser::getId, c -> c.max())
    select(SysUser::getId, c -> c.avg())
```

### 1.2 运算（加,减,乘,除）
```
    select(SysUser::getId, c -> c.plus(1))
    select(SysUser::getId, c -> c.subtract(1))
    select(SysUser::getId, c -> c.multiply(1))
    select(SysUser::getId, c -> c.divide(1))
```
### 1.3 其他函数
    abs，
    pow，
    concat，
    concatAs，
    round，
    if,
    case when
    比较函数
    gte,gt,lt,lte 等等还很多

# 复杂SQL示例
## exists
```
    List<SysUser> list = sysUserMapper.list(new Query() {{
        select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id)
                .from(SysUser.class)
                .exists(new SubQuery()
                        .select1()
                        .from(SysUser.class)
                        .eq(SysUser::getId, $(SysUser::getId))
                        .isNotNull(SysUser::getPassword)
                        .limit(1)
                );

    }});
```
## in 一张表的数据
``` 
List<SysUser> list = sysUserMapper.list(new Query() {{
    select(SysUser::getId, SysUser::getUserName, SysUser::getRole_id)
            .from(SysUser.class)
            .in(SysUser::getId,new SubQuery()
                    .select(SysUser::getId)
                    .from(SysUser.class)
                    .eq(SysUser::getId, $(SysUser::getId))
                    .isNotNull(SysUser::getPassword)
                    .limit(1)
            );

}});
```
## join 自己
```
    Integer count = sysUserMapper.get(new Query()
            .select(SysUser::getId, FunctionInterface::count)
            .from(SysUser.class)
            .join(JoinMode.INNER, SysUser.class, 1, SysUser.class, 2, on -> on.eq(SysUser::getId, 1, SysUser::getRole_id, 2))
            .setReturnType(Integer.TYPE)
    );
```
## join 子表
```
     SubQuery subQuery = new SubQuery("sub")
            .select(SysRole.class)
            .from(SysRole.class)
            .eq(SysRole::getId, 1);

    List<SysUser> list = sysUserMapper.list(new Query()
            .select(SysUser.class)
            .from(SysUser.class)
            .join(JoinMode.INNER, SysUser.class, subQuery, on -> on.eq(SysUser::getRole_id, subQuery.$(subQuery, SysRole::getId)))
    );
```
## select 1 or  select *
```
    new Query().select1();
    new Query().selectAll();
```
## select count(1) or select count(*)
```
    new Query().selectCount1();
    new Query().selectCountAll();
```


# 如何创建条件，列，表等
>    Query类中有个方法，专门提供创建sql的工厂类，new Query().$()
> 
>    可创建 条件，列，表，函数等等，例如

```
    new Query().$().table(...) //创建表
    new Query().$().field(...) //创建表的列
    new Query().$().columnName(...) //获取数据库列名
    new Query().$().gt(...) //创建大于的条件
```

    结合实际使用,例如：

```
    new Query() {{
        select(SysUser::getRole_id)
        .from(SysUser.class)
        .eq($().field(SysUser::getId), 1)
        .gt($().table(SysUser.class).$("role_id"), 2);
    }};
```

    甚至 Query 也包含了部分创建 列 表的功能

```
    new Query() {{
        select($(SysUser::getId))
        .from($(SysUser.class))
        .eq($("id"), 1);
    }};
```
# 如何支持不同数据库
## 如何配置不同类型的数据库
```
mybatis:
  configuration:
    databaseId: MYSQL
```
## 目前支持的类型
> 
> MYSQL，SQL_SERVER，PGSQL，ORACLE
> 
> 其实也支持其他数据库，只是作者没有放开，如有需要，可联系作者放开！
> 
# 如何扩展 SQL指令
## 1.继承 Cmd 实现 sql 方法即可
## 2.联系作者 帮忙 实现
> 扩展起来，非常方便，不过最好是联系作者，这样可以让更多开发者使用！

# 支持作者，赏作者一盒盒饭（^o^）
<img src="./doc/image/alipay.png" style="width:500px">