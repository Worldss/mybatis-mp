package db.sql.api.impl.cmd.basic;

import db.sql.api.Cmd;
import db.sql.api.cmd.basic.Alias;

/**
 * 数据集 可能是一个table 也可以能是一个子查询 等
 */
public interface Dataset<T extends Dataset> extends Cmd, Alias<T> {

    String getPrefix();

    T setPrefix(String prefix);

    /**
     * 创建列字段
     *
     * @param name
     * @return
     */
    default DatasetField $(String name) {
        return new DatasetField(this, name);
    }

    /**
     * 创建所有列字段
     *
     * @return
     */
    default AllField $allField() {
        return new AllField(this);
    }
}
