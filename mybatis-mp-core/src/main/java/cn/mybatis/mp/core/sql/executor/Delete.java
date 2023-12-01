package cn.mybatis.mp.core.sql.executor;

import db.sql.api.impl.cmd.struct.Where;

public class Delete extends BaseDelete<Delete> {

    public Delete() {
        super();
    }

    public Delete(Where where) {
        super(where);
    }
}
