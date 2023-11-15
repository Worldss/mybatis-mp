package cn.mybatis.mp.generator.config;

import cn.mybatis.mp.generator.database.meta.ColumnInfo;
import cn.mybatis.mp.generator.database.meta.TableInfo;
import cn.mybatis.mp.generator.strategy.NamingStrategy;
import lombok.Getter;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
public class EntityConfig {

    /**
     * 是否使用 lombok
     */
    private boolean lombok = true;

    /**
     * 注解上是否加上schema
     */
    private boolean schema = false;

    /**
     * 默认TableId代码，数据库非自增时生效
     */
    private String defaultTableIdCode;

    /**
     * 实体类包名
     */
    private String packageName = "DO";

    /**
     * 实体类名字转换器
     */
    private Function<TableInfo, String> nameConvert;

    /**
     * 实体类父类
     */
    private String superClass;


    private Set<String> superClassColumns = new HashSet<>();

    /**
     * 字段名策略
     */
    private NamingStrategy fieldNamingStrategy = NamingStrategy.UNDERLINE_TO_CAMEL;

    /**
     * 字段名转换器
     */
    private Function<ColumnInfo, String> fieldNameConverter;

    /**
     * 备注转换器
     */
    private Function<ColumnInfo, String> remarksConverter;


    private Map<JdbcType, Class<?>> jdbcTypeClassMap = new HashMap<>();

    {
        jdbcTypeClassMap.put(JdbcType.BIT, Boolean.class);
        jdbcTypeClassMap.put(JdbcType.TINYINT, Byte.class);
        jdbcTypeClassMap.put(JdbcType.SMALLINT, Integer.class);
        jdbcTypeClassMap.put(JdbcType.INTEGER, Integer.class);
        jdbcTypeClassMap.put(JdbcType.BIGINT, Long.class);
        jdbcTypeClassMap.put(JdbcType.FLOAT, Double.class);
        jdbcTypeClassMap.put(JdbcType.REAL, Double.class);
        jdbcTypeClassMap.put(JdbcType.DOUBLE, Double.class);
        jdbcTypeClassMap.put(JdbcType.NUMERIC, BigDecimal.class);
        jdbcTypeClassMap.put(JdbcType.DECIMAL, BigDecimal.class);
        jdbcTypeClassMap.put(JdbcType.CHAR, Character.class);
        jdbcTypeClassMap.put(JdbcType.VARCHAR, String.class);
        jdbcTypeClassMap.put(JdbcType.LONGVARCHAR, String.class);
        jdbcTypeClassMap.put(JdbcType.DATE, LocalDateTime.class);
        jdbcTypeClassMap.put(JdbcType.TIME, LocalTime.class);
        jdbcTypeClassMap.put(JdbcType.TIMESTAMP, LocalTime.class);
        jdbcTypeClassMap.put(JdbcType.BINARY, byte[].class);
        jdbcTypeClassMap.put(JdbcType.VARBINARY, byte[].class);
        jdbcTypeClassMap.put(JdbcType.LONGVARBINARY, byte[].class);
        jdbcTypeClassMap.put(JdbcType.BLOB, byte[].class);
        jdbcTypeClassMap.put(JdbcType.CLOB, String.class);
        jdbcTypeClassMap.put(JdbcType.BOOLEAN, Boolean.class);
        jdbcTypeClassMap.put(JdbcType.NVARCHAR, String.class);
        jdbcTypeClassMap.put(JdbcType.NCHAR, String.class);
        jdbcTypeClassMap.put(JdbcType.NCLOB, String.class);
        jdbcTypeClassMap.put(JdbcType.LONGNVARCHAR, String.class);
        jdbcTypeClassMap.put(JdbcType.NCLOB, String.class);
        jdbcTypeClassMap.put(JdbcType.DATETIMEOFFSET, OffsetDateTime.class);
        jdbcTypeClassMap.put(JdbcType.TIME_WITH_TIMEZONE, OffsetTime.class);
        jdbcTypeClassMap.put(JdbcType.TIMESTAMP_WITH_TIMEZONE, OffsetDateTime.class);
    }

    /**
     * 设置是否使用 lombok
     */
    public EntityConfig lombok(boolean lombok) {
        this.lombok = lombok;
        return this;
    }

    /**
     * 设置是否生成 schema
     *
     * @param schema
     * @return
     */
    public EntityConfig schema(boolean schema) {
        this.schema = schema;
        return this;
    }

    /**
     * 包名设置
     * @param packageName
     * @return
     */
    public EntityConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    /**
     * 字段类型映射
     * @param consumer
     * @return
     */
    public EntityConfig typeMapping(Consumer<Map<JdbcType, Class<?>>> consumer) {
        consumer.accept(this.jdbcTypeClassMap);
        return this;
    }


    /**
     * 实体类名字转换器
     */
    public EntityConfig nameConvert(Function<TableInfo, String> nameConvert) {
        this.nameConvert = nameConvert;
        return this;
    }

    /**
     * 实体类的父类
     *
     * @param superClass
     * @return
     */
    public EntityConfig superClass(String superClass, String... superClassColumns) {
        this.superClass = superClass;
        this.superClassColumns.addAll(Arrays.asList(superClassColumns));
        return this;
    }

    /**
     * 字段名字策略
     */
    public EntityConfig fieldNamingStrategy(NamingStrategy fieldNamingStrategy) {
        this.fieldNamingStrategy = fieldNamingStrategy;
        return this;
    }

    /**
     * 字段名字转换器
     */
    public EntityConfig fieldNameConverter(Function<ColumnInfo, String> fieldNameConverter) {
        this.fieldNameConverter = fieldNameConverter;
        return this;
    }

    /**
     * 备注转换器
     */
    public EntityConfig remarksConverter(Function<ColumnInfo, String> remarksConverter) {
        this.remarksConverter = remarksConverter;
        return this;
    }

    /**
     * 默认TableId代码，数据库非自增时生效
     *
     * @param defaultTableIdCode
     * @return
     */
    public EntityConfig defaultTableIdCode(String defaultTableIdCode) {
        this.defaultTableIdCode = defaultTableIdCode;
        return this;
    }
}
