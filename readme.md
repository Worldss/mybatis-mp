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

> 添加此依赖，无需再添加mybatis依赖
> 包含 mybatis-spring-boot-starter 所有功能
> 配置参数，参考 https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/zh/index.html
> 新增配置项：（其他配置和mybatis-spring-boot-starter 一直）
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
@Table
public class Student {
@TableId(value = IdAutoType.SQL, sql = "SELECT LAST_INSERT_ID()")
private Integer id;
}
> @TableId 支持不同数据库自增或序列 以及 自定义SQL，默认是数据库自增
### 3. @TableField 数据库字段注解
@Table
public class Student {
     
    @TableField(typeHandler = LocalDateTimeTypeHandler.class)
    private LocalDateTime createTime;
}
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
### 1.mybatis Mapper要去
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
### 1.4 高级查询
```agsl
    Query query = new Query() {{
        Table userTable = $.table("sys_user");
        Table roleTable = $.table("sys_role");
        select(
                $.field(userTable, "role_id").as("user_role_id"),
                $.field(roleTable, "name").as("role_name")
        );
        from(userTable);
        join(roleTable).on(
                $.eq($.field(userTable, "role_id"), $.field(roleTable, "id")),
                $.eq($.value(1), $.value(1))
        );

        SubQuery subQuery;
        join(subQuery = new SubQuery("a") {{
            select($.field(userTable, "id"));
            from(userTable);
        }}).on(
                $.eq($.field(userTable, "id"), $.field(subQuery, "id"))
        );
        where($.in($.field(userTable, "id")).add($.value(1), $.value(2)));

        where($.gt($.field(userTable, "id"), $.value(1)));

        where(() -> {
            return $.eq($.field(userTable, "id"), $.field(subQuery, "id"));
        });

        where().and(() -> {
            return null;
        }).and(() -> {
            return $.or(
                    $.eq($.field(userTable, "id"), $.field(subQuery, "ixxxd")),
                    $.eq($.field(userTable, "id"), $.field(subQuery, "aaa"))
            );
        });
    }};
    SQLPrinter.print(query);
```

> 输出 : SELECT t. *  , t.role_id user_role_id , t2.name role_name FROM sys_user t INNER JOIN  sys_role t2 ON t.role_id = t2.id AND 1 = 1 INNER JOIN   (SELECT t.id FROM sys_user t) a  ON t.id = a.id WHERE t.id IN  (1 , 2)  AND t.id > 1 AND t.id = a.id AND  (t.id = a.ixxxd OR t.id = a.aaa)  OR t.id = a.ixxxd OR t.id = a.aaa

> 高级复杂查询，和原生SQL没太多区别

## 2.更新

### 2.1 单表更新
```agsl
    Update update = new Update() {{
        Table userTable = $.table("sys_user");
        update(
                userTable
        );
        TableField id = $.field(userTable, "id");
        updateSet($.field(userTable, "anme"), $.value("小李"));
        where().and(
                $.eq($.field(userTable, "id"), $.value(1))
        );
    }};
    
    SQLPrinter.print(update);
```

> 输出 : UPDATE sys_user t SET t.name = '小李' WHERE t.id = 1

### 2.1 多表更新
```agsl
    Update update = new Update() {{
        Table userTable = $.table("sys_user");
        Table roleTable = $.table("sys_role");
        update(
                userTable
        );
        TableField id = $.field(userTable, "id");
        join(roleTable).on(
                $.eq($.field(userTable, "role_id"), $.field(roleTable, "id")),
                $.eq($.value(1), $.value(1))
        );
        updateSet(id, $.value(1));
        where().and($.eq($.field(userTable, "id"), $.value(1)));
    }};

    SQLPrinter.print(update);
```

> 输出 : UPDATE sys_user t , sys_role t2 SET t.id = 1 WHERE t.role_id = t2.id AND t.id = 1

或者

```agsl
    Update update = new Update() {{
        Table userTable = $.table("sys_user");
        Table roleTable = $.table("sys_role");
        update(
                userTable, roleTable
        );
        TableField id = $.field(userTable, "id");
        updateSet(id, $.value(1));
        where(
                $.eq($.field(userTable, "role_id"), $.field(roleTable, "id")),
                $.eq($.field(userTable, "id"), $.value(1))
        );
    }};

    SQLPrinter.print(update);
```

> 输出 : UPDATE sys_user t INNER JOIN  sys_role t2 ON t.role_id = t2.id AND 1 = 1 SET t.id = 1 WHERE t.id = 1

## 3.删除

### 3.1 单表删除
```agsl
    Delete delete = new Delete() {{
        Table userTable = $.table("sys_user");
        delete();
        from(userTable);
        where($.eq($.field(userTable, "id"), $.value(1)));
    }};

    SQLPrinter.print(delete);
```

> 输出 : DELETE t FROM sys_user t WHERE t.id = 1

### 3.1 多表删除
```agsl
    Delete delete = new Delete() {{
        Table userTable = $.table("sys_user");
        Table roleTable = $.table("sys_role");
        delete(
                userTable, roleTable
        );
        from(userTable);
        join(roleTable).on(
                $.eq($.field(userTable, "role_id"), $.field(roleTable, "id")),
                $.eq($.value(1), $.value(1))
        );
        where($.eq($.field(userTable, "id"), $.value(1)));
    }};

    SQLPrinter.print(delete);
```

> 输出 : DELETE t , t2 FROM sys_user t INNER JOIN  sys_role t2 ON t.role_id = t2.id AND 1 = 1 WHERE t.id = 1

## 4.插入

### 4.1 基础插入
```agsl
    Insert insert = new Insert() {{
        Table userTable = $.table("sys_user");
        insert(userTable);
        fields($.field(userTable, "id"), $.field(userTable, "name"));
        values($.value(1), $.value("小明"));
    }};
    SQLPrinter.print(insert);
```

> 输出 : INSERT INTO sys_user (id , user_name)  VALUES  (1 , '小明')

### 4.2 基础插入 - 批量
```agsl
    Inserts insert = new Inserts() {{
        Table userTable = $.table("sys_user");
        insert(userTable);
        fields($.field(userTable, "id"), $.field(userTable, "user_name"));
        values($.value(1), $.value("小明"));
        values($.value(2), $.value("小王"));
    }};
    SQLPrinter.print(insert);
```

> 输出 : INSERT INTO sys_user (id , user_name)  VALUES  (1 , '小明')  ,  (2 , '小王')

### 4.2 insert into from 插入
```agsl
    InsertFrom insert = new InsertFrom() {{
        Table userTable = $.table("sys_user");
        insert(userTable);
        fields($.field(userTable, "id"), $.field(userTable, "user_name"));
        from(new Query() {{
            select(
                    $.field(userTable,"id"),
                    $.field(userTable,"user_name")
            );
            from(userTable);
            where(
                 $.eq($.field(userTable,"id"), $.value(1))
            );
        }});
    }};
    SQLPrinter.print(insert);
```

> 输出 : INSERT INTO sys_user (id , user_name) SELECT t.id , t.user_name FROM sys_user t WHERE t.id = 1

### 4.3 select into 插入
```agsl
    SelectInsert insert = new SelectInsert() {{
        Table userTable = $.table("sys_user");
        insert($.table("sys_user2"));
        from(new Query() {{
            select(
                    $.field(userTable,"id"),
                    $.field(userTable,"user_name")
            );
            from(userTable);
            where(
                    $.eq($.field(userTable,"id"), $.value(1))
            );
        }});
    }};
    SQLPrinter.print(insert);
```

> 输出 : CREATE TABLE sys_user2 (SELECT t.id , t.user_name FROM sys_user t WHERE t.id = 1)

## 5.如何扩展 继承
### 5.1 实现 Cmd 接口

```agsl
public class XX implements Cmd {
```

### 5.2 实现 Cmd 接口 sql 方法

```agsl
public class XX implements Cmd {

    @Override
    public StringBuilder sql(SqlBuilderContext context, StringBuilder sqlBuilder) {
        return sqlBuilder;
    }
}
```

示例：Form 命令的 源码实现

```agsl
public class From implements Cmd {

    private final Table table;

    public From(Table table) {
        this.table = table;
    }

    @Override
    public StringBuilder sql(SqlBuilderContext context, StringBuilder sqlBuilder) {
        sqlBuilder.append(SqlConst.FROM).append(SqlConst.BLANK);
        table.sql(context, sqlBuilder);
        return sqlBuilder;
    }
}
```
>扩展非常简单,如何扩展，可查看已有Cmd指令参考
