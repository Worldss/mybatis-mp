package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.sql.executor.Query;
import com.mybatis.mp.core.test.model.SysRole;
import com.mybatis.mp.core.test.model.SysUser;
import com.mybatis.mp.core.test.testCase.BaseTest;
import db.sql.api.DbType;
import db.sql.api.JoinMode;
import db.sql.api.SQLMode;
import db.sql.api.SqlBuilderContext;
import org.junit.jupiter.api.Test;

public class CountTest extends BaseTest {

    private String getCountSql(Query query) {
        //创建构建SQL的上下文 数据库:MYSQL SQL模式 打印
        SqlBuilderContext sqlBuilderContext = new SqlBuilderContext(DbType.MYSQL, SQLMode.PRINT);
        return query.countSql(sqlBuilderContext, new StringBuilder(), true).toString();
    }

    @Test
    public void optimizeCountSql() {

        check("order by count优化",
                "select count(*) from (select 1 from t_sys_user t where t.id=1) t",
                getCountSql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );

        check("left join count优化",
                "select count(*) from (select 1 from t_sys_user t where t.id=1) t",
                getCountSql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );

        check("多个left join count优化",
                "select count(*) from (select 1 from t_sys_user t where t.id=1) t",
                getCountSql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .join(JoinMode.LEFT, SysUser.class, 1, SysRole.class, 2)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );

        check("join count不优化",
                "select count(*) from (select 1 from t_sys_user t left join sys_role t2 on t2.id=t.role_id where t.id=1 and t2.id=0) t",
                getCountSql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .eq(SysRole::getId, 0)
                        .orderBy(SysUser::getId)
                )
        );


        check("join count不优化",
                "select count(*) from (select 1 from t_sys_user t right join sys_role t2 on t2.id=t.role_id where t.id=1 and t2.id=0) t",
                getCountSql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.RIGHT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .eq(SysRole::getId, 0)
                        .orderBy(SysUser::getId)
                )
        );


        check("distinct count 不优化",
                "select count(*) from (select distinct t.id,t.user_name from t_sys_user t left join sys_role t2 on t2.id=t.role_id where t.id=1) t",
                getCountSql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );

        check("group by count 不优化",
                "select count(*) from (select 1 from t_sys_user t left join sys_role t2 on t2.id=t.role_id where t.id=1 group by t.id) t",
                getCountSql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .groupBy(SysUser::getId)
                        .orderBy(SysUser::getId)
                )
        );

        check("union count 不优化",
                "select count(*) from (select t.id,t.user_name from t_sys_user t where t.id=1 union select t.id,t.user_name from t_sys_user t where t.id=2 order by t.id asc) t",
                getCountSql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                        .union(new Query()
                                .select(SysUser::getId, SysUser::getUserName)
                                .from(SysUser.class)
                                .eq(SysUser::getId, 2)
                                .orderBy(SysUser::getId))
                )
        );
    }
}
