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

## Maven 集成

```
<dependency>
    <groupId>cn.mybatis-mp</groupId>
    <artifactId>mybatis-mp-spring-boot-starter</artifactId>
    <version>1.0.4</version>
</dependency>  
```

> 1.添加此依赖，无需再添加mybatis依赖
> 
> 2.包含 mybatis-spring-boot-starter 所有功能
> 
> 3.配置参数，参考 https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/zh/index.html
>
> 4.mybatis-mp 配置，可不加（，启动项目时执行，命名特殊时修改）
```
MybatisMpConfig.setTableUnderline(true); //数据库表是否下划线规则 默认 true
MybatisMpConfig.setColumnUnderline(true); ///数据库列是否下划线规则 默认 true
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
> @ResultEntity 用于返回类 和 实体类关联 (只能配置一个，哪个返回列多，配哪个实体类)
> 
> @ResultEntityField 用于解决字段名字和实体类名字不一致的问题
> 
> @NestedResultEntity 用于内嵌类的映射 和 @ResultEntity 类似
> 
> @NestedResultEntityField 用于解决内嵌字段名字和实体类名字不一致的问题
> 
> @ResultField 返回列的映射（用于非实体类字段，可以设置列名、typeHandler、jdbcType）
> 
> 返回可以平级 或者 1级 2级 两层映射
### 1.mybatis Mapper 实现
> 需要继承 MybatisMapper
```java
public interface StudentMapper extends MybatisMapper<Student> {
    
}
```

### 2.Mapper方法

####  getById(Serializable id) 根据ID查询实体
####  <R> R get(Query query) 单个动态查询（可自定返回类型）

####  deleteById(Serializable id) 根据ID删除
####  delete(T entity) 根据实体类删除
####  delete(Delete delete) 动态删除


####  save(T entity) 实体类保存
####  save(Model entity) 实体类部分保存
####  save(Insert insert) 动态插入（无法返回ID）

####  update(T entity) 实体类更新
####  update(T entity,F... fields) 实体类更新 + 强制字段更新
####  update(Model entity) 实体类部分更新
####  update(Model entity,F... fields) 实体类部分更新 + 强制字段更新
####  update(Update update) 动态更新

#### <R> List<R> list(Query query) 列表动态查询（可自定返回类型）
#### List<T> all() 查询所有数据

#### count(Query query) 动态count查询

#### Pager<R> paging(Query query, Pager<R> pager) 分页查询（可自定返回类型）

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
>  join 可结合 @ForeignKey使用 这样无需加 ON 条件 或者 使用下面的方式 join
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
### 1.3 删除

```agsl
     studentMapper.deleteById(1);
     或
     studentMapper.delete(new Delete().from(Student.class).eq(Student::getId,1));
```
> 能用前者优先前者，后者为单个动态删除
>
### 1.4 新增

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

### 1.4 更新

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