# mybatis-mp ，诚邀各位进行参与补充

## 特征

#### 1、很轻量
> 轻量级封装mybatis。

#### 2、高性能
> 对比其他mybatis框架，性能不差，接近最优。

#### 3、灵活方便
> 中高度实现ORM，查询API零学习成本。

#### 4、高可用
> 可应付90%的SQL需求。

## QQ 群

群号： 917404304 ,邀请各位大神参与补充，绝对开源，大家都可以进行代码提交，审核通过会进行master分支。

## Maven 继承

```
<dependency>
    <groupId>org.mybatis-mp</groupId>
    <artifactId>mybatis-mp-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>  
```

> 1.添加此依赖，无需再添加mybatis依赖
> 
> 2.包含 mybatis-spring-boot-starter 所有功能
> 
> 3.配置参数，参考 https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/zh/index.html
>
> 4.新增配置项：（其他配置和mybatis-spring-boot-starter 一致）
```azure
mybatis:
    configuration:
        columnUnderline: true # 列名是否下划线 默认 true
        tableUnderline: true # 表名是否下划线 默认 true
```

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
### 4. @Ignore 可忽略字段
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
### 5. 非实体类结果映射
```java
@Data
@ToString(callSuper = true)
@ResultTable(Student.class)
public class StudentVo {

    private Integer id;

    private String name;

    //private LocalDateTime stCreateTime;

    @ResultField(target = Student.class, property = "createTime")
    private LocalDateTime st2CreateTime;
}

@Data
@ToString(callSuper = true)
@ResultTable(Student.class)
@ResultTable(value = Achievement.class, prefix = "achievement",columnPrefix = "xx_")
public class StudentAchievementVo extends StudentVo {
    
    private Integer achievementId;

    @NestedResultTable(target =  Achievement.class,columnPrefix = "xx_")
    private Achievement achievement;

    @ResultField(columnPrefix = "xx_")
    private Integer score;

}
```
> @ResultTable 用于返回类 和 实体类关联
> 
> @ResultField 用于解决规则特殊的字段名称
> 
> @NestedResultTable 用于内嵌类的映射 和 @ResultTable类似
> 
> @NestedResultField 用于解决内嵌类字段规则特殊的字段名称
> 
> 返回可以平级 或者 1级 2级 两层映射
### 1.mybatis Mapper 实现
> 需要继承 MybatisMapper
```java
public interface StudentMapper extends MybatisMapper<Student> {
    
}
```
### 2.Mapper方法
    #### 2.1 getById(Serializable id) 根据ID查询实体
    #### 2.2 delete(T entity) 根据实体类删除
    #### 2.3 deleteById(Serializable id) 根据ID删除
    #### 2.4 save(T entity) 保存
    #### 2.5 update(T entity) 更新
    #### 2.6 list(Query query) 列表动态查询
    #### 2.7 List<T> all() 查询所有数据
    #### 2.8 count(Query query) 动态count查询
    #### 2.9 Pager<T> paging(Query query, Pager<T> pager) 分页查询
    #### 3.0 <R> R get(Query query) 单个动态查询
## CRUD
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
### 1.4 join 连表查询
```agsl
    List<Achievement> achievementList = achievementMapper.list(new Query()
            .select(Achievement.class)
            .select(Student::getName)
            .from(Achievement.class)
            .join(Achievement.class, Student.class)
            .eq(Achievement::getId, 1)
    );
```
>  join 可结合 @ForeignKey使用 这样无法加 ON 条件 或者 使用下面的方式 join
```agsl
    List<Achievement> achievementList2 = achievementMapper.list(new Query()
            .select(Achievement.class)
            .select(Student::getName)
            .from(Achievement.class)
            .join(Achievement.class, Student.class,on->on.eq(Achievement::getStudentId,Student::getId))
            .eq(Achievement::getId, 1)
    );
```
> 支持各种连接：INNER JOIN ,LEFT JOIN 等等
> 
>