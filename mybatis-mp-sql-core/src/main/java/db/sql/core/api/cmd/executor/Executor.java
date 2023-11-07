package db.sql.core.api.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.cmd.*;
import db.sql.core.api.tookit.CmdUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Executor<SELF extends Executor, CMD_FACTORY extends CmdFactory> extends Cmd {

    CMD_FACTORY $();

    SELF append(Cmd cmd);

    Map<Class<? extends Cmd>, Integer> cmdSorts();

    List<Cmd> cmds();


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

    default List<Cmd> sortedCmds() {
        List<Cmd> cmdList = cmds();
        if (cmdList == null || cmdList.isEmpty()) {
            return cmdList;
        }

        cmdList = cmdList.stream().sorted((o1, o2) -> {
            Integer n1 = cmdSorts().get(o1.getClass());
            Integer n2 = cmdSorts().get(o2.getClass());
            if (n1 == null && n2 == null) {
                return 0;
            }
            if (n1 == null) {
                return 1;
            }
            if (n2 == null) {
                return -1;
            }
            return n1.compareTo(n2);
        }).collect(Collectors.toList());
        return cmdList;
    }

    default StringBuilder sql(SqlBuilderContext context, StringBuilder sqlBuilder) {
        List<Cmd> cmdList = cmds();
        if (cmdList == null || cmdList.isEmpty()) {
            return sqlBuilder;
        }
        return this.sql(this.sortedCmds(), context, sqlBuilder);
    }

    default StringBuilder sql(List<Cmd> sortedCmds, SqlBuilderContext context, StringBuilder sqlBuilder) {
        if (sortedCmds == null || sortedCmds.isEmpty()) {
            return sqlBuilder;
        }
        return CmdUtils.join(null, context, sqlBuilder, sortedCmds);
    }
}
