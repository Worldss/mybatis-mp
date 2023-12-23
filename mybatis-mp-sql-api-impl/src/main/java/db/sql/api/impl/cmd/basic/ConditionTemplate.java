package db.sql.api.impl.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.cmd.basic.ICondition;

/**
 * 条件SQL模板类
 * 采用MessageFormat.format格式化模板
 */
public class ConditionTemplate extends BaseTemplate implements ICondition {

    public static ConditionTemplate create(String template, Object... params){
        return new ConditionTemplate(template,params);
    }

    public static ConditionTemplate create(String template, Cmd... params){
        return new ConditionTemplate(template,params);
    }

    public ConditionTemplate(String template, Object... params) {
        super(template, params);
    }

    public ConditionTemplate(String template, Cmd... params) {
        super(template, params);
    }
}
