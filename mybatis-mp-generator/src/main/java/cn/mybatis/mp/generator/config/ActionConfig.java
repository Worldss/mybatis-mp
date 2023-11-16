package cn.mybatis.mp.generator.config;

import lombok.Getter;

@Getter
public class ActionConfig {

    /**
     * 是否启用
     */
    private boolean enable = true;


    /**
     * 控制器父类
     */
    private String superClass;

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
     * save update 等返回的类型
     */
    private String returnClass;

    /**
     * 返回的名字
     */
    private String returnClassName = "Object";

    /**
     * 设置是否启用
     */
    public ActionConfig enable(boolean enable) {
        this.enable = enable;
        return this;
    }

    /**
     * 控制器父类
     */
    public ActionConfig superClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    /**
     * 是否注入service
     *
     * @param injectService
     * @return
     */
    public ActionConfig injectService(boolean injectService) {
        this.injectService = injectService;
        return this;
    }

    /**
     * 启用泛型
     */
    public ActionConfig generic(boolean generic) {
        this.generic = generic;
        return this;
    }

    /**
     * 是否生成save方法
     *
     * @param save
     * @return
     */
    public ActionConfig save(boolean save) {
        this.save = save;
        return this;
    }

    /**
     * 是否生成update方法
     *
     * @param update
     * @return
     */
    public ActionConfig update(boolean update) {
        this.update = update;
        return this;
    }

    /**
     * 是否生成deleteById方法
     *
     * @param deleteById
     * @return
     */
    public ActionConfig deleteById(boolean deleteById) {
        this.deleteById = deleteById;
        return this;
    }

    /**
     * 是否生成find方法
     *
     * @param find
     * @return
     */
    public ActionConfig find(boolean find) {
        this.find = find;
        return this;
    }

    /**
     * 是否生成getById方法
     *
     * @param getById
     * @return
     */
    public ActionConfig getById(boolean getById) {
        this.getById = getById;
        return this;
    }

    /**
     * 控制器的包名
     *
     * @param packageName
     * @return
     */
    public ActionConfig packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    /**
     * 控制器的后缀
     *
     * @param suffix
     * @return
     */
    public ActionConfig suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    /**
     * 控制器save,update,...等返回的类
     *
     * @param returnClass
     * @return
     */
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


}
