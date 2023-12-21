package db.sql.api.impl.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.basic.ICondition;
import db.sql.api.impl.tookit.SqlConst;
import db.sql.api.tookit.CmdUtils;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * 条件SQL模板类
 * 采用MessageFormat.format格式化模板
 */
public class CmdConditionTemplate implements ICondition {

    private final String template;

    private final Cmd[] params;
    public CmdConditionTemplate(String template, Object... params) {
        this.template = template;
        if (Objects.nonNull(params)) {
            Cmd[] cmds = new Cmd[params.length];
            for (int i = 0; i < params.length; i++) {
                Object param = params[i];
                cmds[i] = param instanceof Cmd ? (Cmd) param : new BasicValue(param);
            }
            this.params = cmds;
        } else {
            this.params = null;
        }
    }

    public CmdConditionTemplate(String template, Cmd... params) {
        this.template = template;
        this.params = params;
    }

    @Override
    public StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        String str = this.template;
        if (Objects.nonNull(params) && params.length > 0) {
            StringBuilder[] paramsStr = new StringBuilder[params.length];
            for (int i = 0; i < params.length; i++) {
                paramsStr[i] = params[i].sql(module, parent, context, new StringBuilder());
            }
            str = MessageFormat.format(this.template, paramsStr);
        }
        return sqlBuilder.append(SqlConst.BLANK).append(str);
    }

    @Override
    public boolean contain(Cmd cmd) {
        if (Objects.isNull(params)) {
            return false;
        }
        return CmdUtils.contain(cmd, params);
    }
}
