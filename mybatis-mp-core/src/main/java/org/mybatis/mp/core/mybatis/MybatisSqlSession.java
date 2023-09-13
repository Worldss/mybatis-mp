package org.mybatis.mp.core.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSession;

public class MybatisSqlSession extends DefaultSqlSession {
    public MybatisSqlSession(Configuration configuration, Executor executor, boolean autoCommit) {
        super(configuration, executor, autoCommit);
    }
}
