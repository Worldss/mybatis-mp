package cn.mybatis.mp.generator.config;

import cn.mybatis.mp.db.IdAutoType;
import cn.mybatis.mp.generator.database.meta.ColumnInfo;
import cn.mybatis.mp.generator.database.meta.TableInfo;
import cn.mybatis.mp.generator.template.*;
import cn.mybatis.mp.generator.template.engine.TemplateEngine;
import db.sql.api.DbType;
import lombok.Getter;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@Getter
public class GeneratorConfig {

    private DataSourceConfig dataSourceConfig;

    public GeneratorConfig(String schema, String jdbcUrl, String username, String password) {
        this.dataSourceConfig = new DataSourceConfig(schema, jdbcUrl, username, password);
    }

    public GeneratorConfig(String schema, DataSource dataSource, DbType dbType) {
        this.dataSourceConfig = new DataSourceConfig(schema, dataSource, dbType);
    }

    /**
     * 忽略试图
     */
    private boolean ignoreView = false;

    /**
     * 忽略表
     */
    private boolean ignoreTable = false;

    /**
     * 完成后是否打开目录
     */
    private boolean finishOpen = false;


    /**
     * 根文件路径 默认取 System.getProperty("user.dir") +"/generate"
     */
    private String baseFilePath = System.getProperty("user.dir") + "/generate";

    /**
     * 根包路径
     */
    private String basePackage = "";

    /**
     *
     */
    private String templateRootPath = "templates";

    /**
     * 作者
     */
    private String author;


    private TemplateEngine templateEngine;

    private List<Class<? extends ITemplateBuilder>> templateBuilders = new ArrayList<>();

    {
        templateBuilders.add(EntityTemplateBuilder.class);
        templateBuilders.add(MapperTemplateBuilder.class);
        templateBuilders.add(MapperXmlTemplateBuilder.class);
        templateBuilders.add(DaoTemplateBuilder.class);
        templateBuilders.add(DaoImplTemplateBuilder.class);
        templateBuilders.add(ServiceTemplateBuilder.class);
        templateBuilders.add(ServiceImplTemplateBuilder.class);
        templateBuilders.add(ActionTemplateBuilder.class);
    }

    /**
     * 需要生成的表名
     */
    private List<String> includeTables = new ArrayList<>();

    /**
     * 需要排除的表名
     */
    private List<String> excludeTables = new ArrayList<>();

    /**
     * mapper 配置
     */
    private MapperConfig mapperConfig = new MapperConfig();

    /**
     * mapper xml 配置
     */
    private MapperXmlConfig mapperXmlConfig = new MapperXmlConfig();

    /**
     * 列配置
     */
    private ColumnConfig columnConfig = new ColumnConfig();

    /**
     * 实体类配置
     */
    private EntityConfig entityConfig = new EntityConfig();

    /**
     * Dao 配置
     */
    private DaoConfig daoConfig = new DaoConfig();

    /**
     * Dao 实现类配置
     */
    private DaoImplConfig daoImplConfig = new DaoImplConfig();

    /**
     * Service 配置
     */
    private ServiceConfig serviceConfig = new ServiceConfig();

    /**
     * Service 实现类配置
     */
    private ServiceImplConfig serviceImplConfig = new ServiceImplConfig();

    /**
     * Action 实现类配置
     */
    private ActionConfig actionConfig = new ActionConfig();

    /**
     * 设置模板引擎
     *
     * @param templateEngine
     * @return
     */
    public GeneratorConfig templateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        return this;
    }

    /**
     * 设置模板的根目录 默认：templates
     *
     * @param templateRootPath
     * @return
     */
    public GeneratorConfig templateRootPath(String templateRootPath) {
        this.templateRootPath = templateRootPath;
        return this;
    }

    /**
     * 设置 模板生成器
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig templateBuilders(Consumer<List<Class<? extends ITemplateBuilder>>> consumer) {
        consumer.accept(this.templateBuilders);
        return this;
    }

    /**
     * 设置文件生成目标目录 默认： System.getProperty("user.dir") +"/generate"
     *
     * @param baseFilePath
     * @return
     */
    public GeneratorConfig baseFilePath(String baseFilePath) {
        this.baseFilePath = baseFilePath;
        return this;
    }

    /**
     * 设置 基础包路径
     *
     * @param basePackage
     * @return
     */
    public GeneratorConfig basePackage(String basePackage) {
        this.basePackage = basePackage;
        return this;
    }

    /**
     * 设置作者
     *
     * @param author
     * @return
     */
    public GeneratorConfig author(String author) {
        this.author = author;
        return this;
    }

    /**
     * 设置是否忽略试图
     *
     * @param ignoreView
     * @return
     */
    public GeneratorConfig ignoreView(boolean ignoreView) {
        this.ignoreView = ignoreView;
        return this;
    }

    /**
     * 设置需要生成的表
     *
     * @param tables
     * @return
     */
    public GeneratorConfig includeTable(String... tables) {
        this.includeTables.addAll(Arrays.asList(tables));
        return this;
    }

    /**
     * 设置需要不生成的表
     *
     * @param tables
     * @return
     */
    public GeneratorConfig excludeTable(String... tables) {
        this.excludeTables.addAll(Arrays.asList(tables));
        return this;
    }

    /**
     * 设置列的配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig columnConfig(Consumer<ColumnConfig> consumer) {
        consumer.accept(this.columnConfig);
        return this;
    }

    /**
     * 设置实体类的配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig entityConfig(Consumer<EntityConfig> consumer) {
        consumer.accept(this.entityConfig);
        return this;
    }


    /**
     * 设置mapper配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig mapperConfig(Consumer<MapperConfig> consumer) {
        consumer.accept(this.mapperConfig);
        return this;
    }

    /**
     * 设置mapper配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig mapperXmlConfig(Consumer<MapperXmlConfig> consumer) {
        consumer.accept(this.mapperXmlConfig);
        return this;
    }

    /**
     * 设置dao配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig daoConfig(Consumer<DaoConfig> consumer) {
        consumer.accept(this.daoConfig);
        return this;
    }

    /**
     * 设置dao 实现类的配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig daoImplConfig(Consumer<DaoImplConfig> consumer) {
        consumer.accept(this.daoImplConfig);
        return this;
    }

    /**
     * 设置service配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig serviceConfig(Consumer<ServiceConfig> consumer) {
        consumer.accept(this.serviceConfig);
        return this;
    }

    /**
     * 设置 service 实现类配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig serviceImplConfig(Consumer<ServiceImplConfig> consumer) {
        consumer.accept(this.serviceImplConfig);
        return this;
    }

    /**
     * 设置 控制器的配置
     *
     * @param consumer
     * @return
     */
    public GeneratorConfig actionConfig(Consumer<ActionConfig> consumer) {
        consumer.accept(this.actionConfig);
        return this;
    }

    /**
     * 完成后打开
     */
    public GeneratorConfig finishOpen(boolean finishOpen) {
        this.finishOpen = finishOpen;
        return this;
    }
}
