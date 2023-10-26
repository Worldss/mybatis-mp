package cn.mybatis.mp.core.sql.executor;

import db.sql.core.api.cmd.executor.AbstractInsert;

public class Insert extends AbstractInsert<Insert, MybatisCmdFactory> {
    public Insert() {
        super(new MybatisCmdFactory());
    }
}
