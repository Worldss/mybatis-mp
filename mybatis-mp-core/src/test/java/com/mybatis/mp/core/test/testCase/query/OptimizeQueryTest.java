package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.sql.executor.Query;
import com.mybatis.mp.core.test.model.SysRole;
import com.mybatis.mp.core.test.model.SysUser;
import com.mybatis.mp.core.test.testCase.BaseTest;
import db.sql.api.DbType;
import db.sql.api.JoinMode;
import db.sql.api.SQLMode;
import db.sql.api.SqlBuilderContext;
import db.sql.core.api.tookit.SQLOptimizeUtils;
import db.sql.core.api.tookit.SQLPrinter;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

public class OptimizeQueryTest extends BaseTest {

    private String getQuerySql(Query query) {
        //创建构建SQL的上下文 数据库:MYSQL SQL模式 打印
        SqlBuilderContext sqlBuilderContext = new SqlBuilderContext(DbType.MYSQL, SQLMode.PRINT);
        String sql = SQLPrinter.sql(query);
        String str = SQLOptimizeUtils.getOptimizedSql(query, sqlBuilderContext, new StringBuilder()).toString();
        Assert.assertEquals("sql 优化破坏了原来有query", sql, SQLPrinter.sql(query));
        return str;
    }

    @Test
    public void simpleOrderBy() {
        check("order by 优化后的SQL",
                "select t.id,t.user_name from t_sys_user t where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }


    @Test
    public void simpleOrderByLimit() {
        check("order by 优化后的SQL",
                "select t.id,t.user_name from t_sys_user t where t.id=1 order by t.id asc limit 2 offset 0 ",
                getQuerySql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                        .limit(0, 2)
                )
        );
    }

    @Test
    public void simpleDistinctOrderBy() {
        check("distinct order by 优化后的SQL",
                "select distinct t.id,t.user_name from t_sys_user t where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void simpleDistinctOrderByLimit() {
        check("distinct order by 优化后的SQL",
                "select distinct t.id,t.user_name from t_sys_user t where t.id=1 order by t.id asc limit 2 offset 0",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                        .limit(0, 2)
                )
        );
    }

    @Test
    public void leftJoinOrderBy() {
        check("未使用left join 表 order by 优化后的SQL",
                "select t.id,t.user_name from t_sys_user t where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void leftJoinOrderBy2() {
        check("order by 优化后的SQL",
                "select t.id,t.user_name,t2.id from t_sys_user t left join sys_role t2 on t2.id=t.role_id where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .select(SysRole::getId)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void distinctLeftJoinOrderBy() {
        check("order by 优化后的SQL",
                "select distinct t.id,t.user_name from t_sys_user t where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void distinctLeftJoinOrderByLimit() {
        check("order by 优化后的SQL",
                "select distinct t.id,t.user_name from t_sys_user t where t.id=1 order by t.id asc limit 1 offset 0",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                        .limit(0, 1)
                )
        );
    }

    @Test
    public void distinctLeftJoinOrderBy2() {
        check("order by 优化后的SQL",
                "select  t.id,t.user_name,t2.id from t_sys_user t left join sys_role t2 on t2.id=t.role_id where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .select(SysRole::getId)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void distinctLeftJoinOrderBy2Limit() {
        check("order by 优化后的SQL",
                "select distinct t.id,t.user_name,t2.id from t_sys_user t left join sys_role t2 on t2.id=t.role_id where t.id=1 order by t.id asc limit 2 offset 0",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .select(SysRole::getId)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                        .limit(0, 2)
                )
        );
    }


    @Test
    public void rightJoinOrderBy() {
        check("right join order by 优化后的SQL",
                "select t.id,t.user_name from t_sys_user t right join sys_role t2 on t2.id=t.role_id where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.RIGHT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void rightJoinOrderBy2() {
        check("right join order by 优化后的SQL",
                "select t.id,t.user_name,t2.id from t_sys_user t right join sys_role t2 on t2.id=t.role_id where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .select(SysRole::getId)
                        .from(SysUser.class)
                        .join(JoinMode.RIGHT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void distinctRightJoinOrderBy() {
        check("right join order by 优化后的SQL",
                "select distinct t.id,t.user_name from t_sys_user t right join sys_role t2 on t2.id=t.role_id where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.RIGHT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void distinctRightJoinOrderByLimit() {
        check("right join order by 优化后的SQL",
                "select distinct t.id,t.user_name from t_sys_user t right join sys_role t2 on t2.id=t.role_id where t.id=1 order by t.id asc limit 1 offset 0",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.RIGHT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                        .limit(0, 1)
                )
        );
    }

    @Test
    public void distinctRightJoinOrderBy2() {
        check("right join order by 优化后的SQL",
                "select distinct t.id,t.user_name,t2.id from t_sys_user t right join sys_role t2 on t2.id=t.role_id where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .select(SysRole::getId)
                        .from(SysUser.class)
                        .join(JoinMode.RIGHT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void distinctRightJoinOrderBy2Limit() {
        check("right join order by 优化后的SQL",
                "select distinct t.id,t.user_name,t2.id from t_sys_user t right join sys_role t2 on t2.id=t.role_id where t.id=1 order by t.id asc limit 2 offset 0",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .select(SysRole::getId)
                        .from(SysUser.class)
                        .join(JoinMode.RIGHT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                        .limit(0, 2)
                )
        );
    }


    @Test
    public void leftJoin2OrderBy() {
        check("order by 优化后的SQL",
                "select t.id,t.user_name from t_sys_user t where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .join(JoinMode.LEFT, SysUser.class, 1, SysRole.class, 2)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void leftJoin2OrderBy2() {
        check("order by 优化后的SQL",
                "select t.id,t.user_name,t2.id from t_sys_user t left join sys_role t2 on t2.id=t.role_id where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .select(SysRole::getId)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .join(JoinMode.LEFT, SysUser.class, 1, SysRole.class, 2)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void distinctLeftJoin2OrderBy() {
        check("order by 优化后的SQL",
                "select distinct t.id,t.user_name from t_sys_user t where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .join(JoinMode.LEFT, SysUser.class, 1, SysRole.class, 2)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void distinctLeftJoin2OrderByLimit() {
        check("order by 优化后的SQL",
                "select distinct t.id,t.user_name from t_sys_user t where t.id=1 order by t.id asc limit 1 offset 0",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .join(JoinMode.LEFT, SysUser.class, 1, SysRole.class, 2)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                        .limit(0, 1)
                )
        );
    }

    @Test
    public void distinctLeftJoin2OrderBy2() {
        check("order by 优化后的SQL",
                "select distinct t.id,t.user_name,t2.id from t_sys_user t left join sys_role t2 on t2.id=t.role_id where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .select(SysRole::getId)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .join(JoinMode.LEFT, SysUser.class, 1, SysRole.class, 2)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void distinctLeftJoin2OrderBy3() {
        check("order by 优化后的SQL",
                "select distinct t.id,t.user_name,t2.id from t_sys_user t left join sys_role t2 on t2.id=t.role_id left join sys_role t3 on t3.id=t.role_id where t.id=1 order by t3.id asc",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .select(SysRole::getId)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .join(JoinMode.LEFT, SysUser.class, 1, SysRole.class, 2)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysRole::getId, 2)
                )
        );
    }

    @Test
    public void distinctLeftJoin2OrderBy2Limit() {
        check("order by 优化后的SQL",
                "select distinct t.id,t.user_name,t2.id from t_sys_user t left join sys_role t2 on t2.id=t.role_id where t.id=1 order by t.id asc limit 2 offset 0",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .select(SysRole::getId)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .join(JoinMode.LEFT, SysUser.class, 1, SysRole.class, 2)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                        .limit(0, 2)
                )
        );
    }


    @Test
    public void leftJoinRIGHTOrderBy() {
        check("order by 优化后的SQL",
                "select t.id,t.user_name from t_sys_user t right join sys_role t3 on t3.id=t.role_id where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .join(JoinMode.RIGHT, SysUser.class, 1, SysRole.class, 2)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void leftJoinRIGHTOrderBy2() {
        check("order by 优化后的SQL",
                "select t.id,t.user_name,t2.id from t_sys_user t left join sys_role t2 on t2.id=t.role_id right join sys_role t3 on t2.id=t3.id where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .select(SysRole::getId)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .join(JoinMode.RIGHT, SysRole.class, 1, SysRole.class, 2, on -> on.eq(SysRole::getId, 1, SysRole::getId, 2))
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void distinctLeftJoinRIGHTOrderBy() {
        check("order by 优化后的SQL",
                "select distinct t.id,t.user_name from t_sys_user t right join sys_role t3 on t3.id=t.role_id where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .join(JoinMode.RIGHT, SysUser.class, 1, SysRole.class, 2)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void distinctLeftJoinRIGHTOrderByLimit() {
        check("order by 优化后的SQL",
                "select distinct t.id,t.user_name from t_sys_user t right join sys_role t3 on t3.id=t.role_id where t.id=1 order by t.id asc limit 1 offset 0",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .join(JoinMode.RIGHT, SysUser.class, 1, SysRole.class, 2)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                        .limit(0, 1)
                )
        );
    }

    @Test
    public void distinctLeftJoinRIGHTOrderBy2() {
        check("order by 优化后的SQL",
                "select distinct t.id,t.user_name,t2.id from t_sys_user t left join sys_role t2 on t2.id=t.role_id right join sys_role t3 on t3.id=t.role_id where t.id=1 order by t.id asc",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .select(SysRole::getId)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .join(JoinMode.RIGHT, SysUser.class, 1, SysRole.class, 2)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                )
        );
    }

    @Test
    public void distinctLeftJoinRIGHTOrderBy2Limit() {
        check("order by 优化后的SQL",
                "select distinct t.id,t.user_name,t2.id from t_sys_user t left join sys_role t2 on t2.id=t.role_id right join sys_role t3 on t3.id=t.role_id where t.id=1 order by t.id asc limit 2 offset 0",
                getQuerySql(new Query()
                        .selectDistinct()
                        .select(SysUser::getId, SysUser::getUserName)
                        .select(SysRole::getId)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .join(JoinMode.RIGHT, SysUser.class, 1, SysRole.class, 2)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                        .limit(0, 2)
                )
        );
    }


    @Test
    public void unionOrderBy() {
        check("unionOrderBy",
                "select t.id,t.user_name from t_sys_user t where t.id=1 order by t.id asc union select t.id,t.user_name from t_sys_user t where t.id=2 order by t.id asc",
                getQuerySql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                        .union(new Query()
                                .select(SysUser::getId, SysUser::getUserName)
                                .from(SysUser.class)
                                .eq(SysUser::getId, 2)
                                .orderBy(SysUser::getId)
                        )
                )
        );
    }

    @Test
    public void unionLeftJoin() {
        check("unionOrderBy",
                "select t.id,t.user_name from t_sys_user t where t.id=1 order by t.id asc union select t.id,t.user_name from t_sys_user t where t.id=2 order by t.id asc",
                getQuerySql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.LEFT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                        .union(new Query()
                                .select(SysUser::getId, SysUser::getUserName)
                                .from(SysUser.class)
                                .eq(SysUser::getId, 2)
                                .orderBy(SysUser::getId)
                        )
                )
        );
    }

    @Test
    public void unionRightJoin() {
        check("unionOrderBy",
                "select t.id,t.user_name from t_sys_user t right join sys_role t2 on t2.id=t.role_id where t.id=1 order by t.id asc union select t.id,t.user_name from t_sys_user t where t.id=2 order by t.id asc",
                getQuerySql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .join(JoinMode.RIGHT, SysUser.class, SysRole.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                        .union(new Query()
                                .select(SysUser::getId, SysUser::getUserName)
                                .from(SysUser.class)
                                .eq(SysUser::getId, 2)
                                .orderBy(SysUser::getId)
                        )
                )
        );
    }

    @Test
    public void unionOrderByLimit() {
        check("unionOrderBy",
                "select t.id,t.user_name from t_sys_user t where t.id=1 order by t.id asc limit 3 offset 0 union select t.id,t.user_name from t_sys_user t where t.id=2 order by t.id asc",
                getQuerySql(new Query()
                        .select(SysUser::getId, SysUser::getUserName)
                        .from(SysUser.class)
                        .eq(SysUser::getId, 1)
                        .orderBy(SysUser::getId)
                        .limit(0, 3)
                        .union(new Query()
                                .select(SysUser::getId, SysUser::getUserName)
                                .from(SysUser.class)
                                .eq(SysUser::getId, 2)
                                .orderBy(SysUser::getId)
                        )
                )
        );
    }
}
