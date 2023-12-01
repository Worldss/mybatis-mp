package cn.mybatis.mp.core.sql.executor;

import db.sql.api.impl.cmd.struct.Where;

public class Update extends BaseUpdate<Update> {

    public Update() {
        super();
    }

    public Update(Where where) {
        super(where);
    }

}
