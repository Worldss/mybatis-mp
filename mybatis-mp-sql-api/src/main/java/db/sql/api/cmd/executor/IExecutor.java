package db.sql.api.cmd.executor;

import db.sql.api.Cmd;
import db.sql.api.Getter;
import db.sql.api.SqlBuilderContext;
import db.sql.api.tookit.CmdUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface IExecutor<T extends IExecutor,
        TABLE extends DATASET,
        DATASET extends Cmd,
        TABLE_FIELD extends DATASET_FILED,
        DATASET_FILED extends Cmd
        >

        extends Cmd {

    Map<Class<? extends Cmd>, Integer> cmdSorts();

    List<Cmd> cmds();

    default TABLE $(Class entity) {
        return this.$(entity, 1);
    }

    TABLE $(Class entity, int storey);

    default <G> TABLE_FIELD $(Getter<G> column) {
        return this.$(column, 1);
    }

    <G> TABLE_FIELD $(Getter<G> column, int storey);

    <G> DATASET_FILED $(DATASET dataset, Getter<G> column);

    DATASET_FILED $(DATASET dataset, String columnName);

    /**
     * 万能创建SQL命令方法
     *
     * @param column 列
     * @param RF     返回函数
     * @param <G>    实体类型
     * @param <R>    返回命令
     * @return
     */
    default <G, R extends Cmd> R $(Getter<G> column, Function<TABLE_FIELD, R> RF) {
        return this.$(column, 1, RF);
    }

    /**
     * 万能创建SQL命令方法
     *
     * @param column 列
     * @param storey 缓存区
     * @param RF     返回函数
     * @param <G>    实体类型
     * @param <R>    返回命令
     * @return
     */
    <G, R extends Cmd> R $(Getter<G> column, int storey, Function<TABLE_FIELD, R> RF);

    /**
     * 内联，用于获取自身
     *
     * @param consumer
     * @return
     */
    default T connect(Consumer<T> consumer) {
        consumer.accept((T) this);
        return (T) this;
    }

    default Comparator<Cmd> comparator() {
        return (o1, o2) -> {
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
        };
    }


    default List<Cmd> sortedCmds() {
        List<Cmd> cmdList = cmds();
        if (cmdList == null || cmdList.isEmpty()) {
            return cmdList;
        }
        Comparator<Cmd> comparator = comparator();
        cmdList = cmdList.stream().sorted(comparator).collect(Collectors.toList());
        return cmdList;
    }


    @Override
    default StringBuilder sql(Cmd module, Cmd parent, SqlBuilderContext context, StringBuilder sqlBuilder) {
        return this.sql(context, sqlBuilder);
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
        return CmdUtils.join(null, null, context, sqlBuilder, sortedCmds);
    }
}
