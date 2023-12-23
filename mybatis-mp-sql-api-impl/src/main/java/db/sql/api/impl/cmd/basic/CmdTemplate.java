package db.sql.api.impl.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.cmd.basic.Alias;

/**
 * SQL模板类
 * 采用MessageFormat.format格式化模板
 */
public class CmdTemplate extends BaseTemplate implements Alias<CmdTemplate> {

    public static CmdTemplate create(String template, Object... params){
        return new CmdTemplate(template,params);
    }

    public static CmdTemplate create(String template, Cmd... params){
        return new CmdTemplate(template,params);
    }

    private String alias;

    public CmdTemplate(String template, Object... params) {
        super(template, params);
    }

    public CmdTemplate(String template, Cmd... params) {
        super(template, params);
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public CmdTemplate as(String alias) {
        this.alias = alias;
        return this;
    }
}
