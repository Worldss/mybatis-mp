package cn.mybatis.mp.generator.config;

import lombok.Getter;

@Getter
public class ActionConfig {

    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * 注入service
     */
    private boolean injectService = true;

    /**
     * 是否含有泛型
     */
    private boolean generic;

    /**
     * 新增
     */
    private boolean save = true;

    /**
     * 修改
     */
    private boolean update = true;

    /**
     * 删除
     */
    private boolean deleteById = true;

    /**
     * 分页
     */
    private boolean find = true;

    /**
     * 单个查询
     */
    private boolean getById = true;

    /**
     * 实体类包名
     */
    private String packageName = "action";

    /**
     * mapper后缀
     */
    private String suffix = "Action";

    /**
     * 接口父类
     */
    private String superClass;

    /**
     * 返回的类型
     */
    private String returnClass;

    private String returnClassName = "void";

    /**
     * 设置是否启用
     */
    public ActionConfig enable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public ActionConfig generic(boolean generic) {
        this.generic = generic;
        return this;
    }

    public ActionConfig save(boolean save) {
        this.save = save;
        return this;
    }

    public ActionConfig update(boolean update) {
        this.update = update;
        return this;
    }

    public ActionConfig deleteById(boolean deleteById) {
        this.deleteById = deleteById;
        return this;
    }

    public ActionConfig find(boolean find) {
        this.find = find;
        return this;
    }

    public ActionConfig getById(boolean getById) {
        this.getById = getById;
        return this;
    }

    public ActionConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    public ActionConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public ActionConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public ActionConfig returnClass(String returnClass) {
        this.returnClass = returnClass;
        int dotIndex = returnClass.lastIndexOf(".");
        if (dotIndex > 0) {
            this.returnClassName = returnClass.substring(dotIndex + 1);
        } else {
            this.returnClassName = returnClass;
        }
        return this;
    }

    public ActionConfig injectService(boolean injectService) {
        this.injectService = injectService;
        return this;
    }
}
