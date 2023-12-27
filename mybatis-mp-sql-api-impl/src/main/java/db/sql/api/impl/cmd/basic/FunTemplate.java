package db.sql.api.impl.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.impl.cmd.dbFun.FunctionInterface;

/**
 * 函数模板
 */
public class FunTemplate extends CmdTemplate implements FunctionInterface {

    public FunTemplate(String template, Object... params) {
        super(template, params);
    }

    public FunTemplate(String template, Cmd... params) {
        super(template, params);
    }

    public static FunTemplate create(String template, Object... params) {
        return new FunTemplate(template, params);
    }

    public static FunTemplate create(String template, Cmd... params) {
        return new FunTemplate(template, params);
    }

}
