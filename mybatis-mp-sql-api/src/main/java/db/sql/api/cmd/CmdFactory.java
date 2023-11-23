package db.sql.api.cmd;

import db.sql.api.Cmd;
import db.sql.api.Getter;

import java.util.function.Consumer;
import java.util.function.Function;

public interface CmdFactory<TABLE extends DATASET, DATASET, TABLE_FIELD, DATASET_FIELD, ON> {

    default TABLE table(Class entity) {
        return this.table(entity, 1);
    }

    /**
     * 根据实体类获取TABLE对象
     *
     * @param entity 实体类
     * @param storey 存储层级
     * @return
     */
    TABLE table(Class entity, int storey);

    /**
     * 根据表名获取TABLE对象
     *
     * @param tableName
     * @return
     */
    TABLE table(String tableName);

    /**
     * 根据Lambda getter 获取列名
     *
     * @param getter
     * @param <T>
     * @return
     */
    <T> String columnName(Getter<T> getter);

    default <T> TABLE_FIELD field(Getter<T> getter) {
        return this.field(getter, 1);
    }

    /**
     * 根据Lambda getter 获取列对象
     *
     * @param getter
     * @param storey 存储层级
     * @param <T>
     * @return
     */
    <T> TABLE_FIELD field(Getter<T> getter, int storey);


    /**
     * 根据dataset(可能是子查询 也可能是表),Lambda getter 创建列对象
     *
     * @param dataset
     * @param getter
     * @param <T>
     * @return
     */
    <T> DATASET_FIELD field(DATASET dataset, Getter<T> getter);

    /**
     * 根据dataset(可能是子查询 也可能是表) 列名，创建 列对象
     *
     * @param dataset
     * @param name
     * @return
     */
    DATASET_FIELD field(DATASET dataset, String name);


    /**
     * 根据实体类 构建新的 Consumer<ON>
     *
     * @param mainTable   主表实体类
     * @param secondTable 副表实体类
     * @param consumer
     * @return
     */
    default Consumer<ON> buildOn(Class mainTable, Class secondTable, Consumer<ON> consumer) {
        return this.buildOn(mainTable, 1, secondTable, 1, consumer);
    }

    /**
     * 根据实体类 构建新的 Consumer<ON>
     *
     * @param mainTable         主表实体类
     * @param mainTableStorey   主表实体类存储层级
     * @param secondTable       副表实体类
     * @param secondTableStorey 副表实体类存储层级
     * @param consumer
     * @return
     */
    Consumer<ON> buildOn(Class mainTable, int mainTableStorey, Class secondTable, int secondTableStorey, Consumer<ON> consumer);


    /**
     * 根据Lambda getter，万能创建SQL命令方法
     *
     * @param getter 列
     * @param RF     返回函数
     * @param <T>    实体类型
     * @param <R>    返回命令
     * @return
     */
    default <T, R extends Cmd> R create(Getter<T> getter, Function<TABLE_FIELD, R> RF) {
        return this.create(getter, 1, RF);
    }

    /**
     * 根据Lambda getter，万能创建SQL命令方法
     *
     * @param getter 列
     * @param storey 缓存区
     * @param RF     返回函数
     * @param <T>    实体类型
     * @param <R>    返回命令
     * @return
     */
    <T, R extends Cmd> R create(Getter<T> getter, int storey, Function<TABLE_FIELD, R> RF);
}
