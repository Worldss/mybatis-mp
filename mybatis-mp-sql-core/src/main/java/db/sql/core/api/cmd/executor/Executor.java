package db.sql.core.api.cmd.executor;

import db.sql.api.Getter;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.Cmd;
import db.sql.api.tookit.CmdUtils;
import db.sql.core.api.cmd.CmdFactory;
import db.sql.core.api.cmd.basic.Dataset;
import db.sql.core.api.cmd.basic.DatasetField;
import db.sql.core.api.cmd.basic.Table;
import db.sql.core.api.cmd.basic.TableField;

import java.util.List;
import java.util.function.Function;

public interface Executor<SELF extends Executor, CMD_FACTORY extends CmdFactory> extends db.sql.api.cmd.executor.Executor<SELF> {

    CMD_FACTORY $();

    SELF append(Cmd cmd);

    default Table $(Class entity) {
        return $().table(entity);
    }

    default Table $(Class entity, int storey) {
        return $().table(entity);
    }

    default <T, R extends Cmd> R $(Class entity, Function<Table, R> RF) {
        return $().create(entity, RF);
    }

    default <T, R extends Cmd> R $(Class entity, int storey, Function<Table, R> RF) {
        return $().create(entity, storey, RF);
    }

    default <T> TableField $(Getter<T> getter) {
        return $().field(getter);
    }

    default <T> DatasetField $(Dataset dataset, Getter<T> getter) {
        return dataset.$($().columnName(getter));
    }

    default <T> TableField $(Getter<T> getter, int storey) {
        return $().field(getter, storey);
    }

    /**
     * 万能创建SQL命令方法
     *
     * @param getter 列
     * @param RF     返回函数
     * @param <T>    实体类型
     * @param <R>    返回命令
     * @return
     */
    default <T, R extends Cmd> R $(Getter<T> getter, Function<TableField, R> RF) {
        return $().create(getter, RF);
    }

    /**
     * 万能创建SQL命令方法
     *
     * @param getter 列
     * @param storey 缓存区
     * @param RF     返回函数
     * @param <T>    实体类型
     * @param <R>    返回命令
     * @return
     */
    default <T, R extends Cmd> R $(Getter<T> getter, int storey, Function<TableField, R> RF) {
        return $().create(getter, storey, RF);
    }

    @Override
    default StringBuilder sql(Cmd user, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return this.sql(context, sqlBuilder);
    }

    @Override
    default StringBuilder sql(SqlBuilderContext context, StringBuilder sqlBuilder) {
        List<Cmd> cmdList = cmds();
        if (cmdList == null || cmdList.isEmpty()) {
            return sqlBuilder;
        }
        cmdList = sortedCmds();
        return CmdUtils.join(null, context, sqlBuilder, cmdList);
    }
}
