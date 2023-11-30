package db.sql.api.impl.cmd.basic;

import db.sql.api.cmd.basic.IDataset;

/**
 * 数据集 可能是一个table 也可以能是一个子查询 等
 */
public interface Dataset<T extends Dataset, FIELD extends DatasetField> extends IDataset<T, FIELD> {
    /**
     * 创建列字段
     *
     * @param name
     * @return
     */
    default FIELD $(String name) {
        return (FIELD) new DatasetField(this, name);
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
