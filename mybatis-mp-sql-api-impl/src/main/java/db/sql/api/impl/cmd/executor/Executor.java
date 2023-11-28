package db.sql.api.impl.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.SqlBuilderContext;
import db.sql.api.impl.cmd.CmdFactory;
import db.sql.api.impl.cmd.basic.Dataset;
import db.sql.api.impl.cmd.basic.DatasetField;
import db.sql.api.impl.cmd.basic.Table;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.tookit.CmdUtils;

import java.util.List;
import java.util.function.Function;

public interface Executor<SELF extends Executor,
        CMD_FACTORY extends CmdFactory
        >
        extends db.sql.api.cmd.executor.Executor<SELF, Table, Dataset, TableField, DatasetField> {

    CMD_FACTORY $();

    SELF append(Cmd cmd);

    @Override
    default Table $(Class entity, int storey) {
        return $().table(entity, storey);
    }

    @Override
    default <T> DatasetField $(Dataset dataset, Getter<T> column) {
        return $().field(dataset, column);
    }

    @Override
    default <T> TableField $(Getter<T> column, int storey) {
        return $().field(column, storey);
    }

    @Override
    default DatasetField $(Dataset dataset, String columnName) {
        return $().field(dataset, columnName);
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
    default <T, R extends Cmd> R $(Getter<T> column, int storey, Function<TableField, R> RF) {
        return $().create(column, storey, RF);
    }

    @Override
    default StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return this.sql(context, sqlBuilder);
    }

    @Override
    default StringBuilder sql(SqlBuilderContext context, StringBuilder sqlBuilder) {
        List<Cmd> cmdList = cmds();
        if (cmdList == null || cmdList.isEmpty()) {
            return sqlBuilder;
        }
        cmdList = sortedCmds();
        return CmdUtils.join(null, null, context, sqlBuilder, cmdList);
    }
}
