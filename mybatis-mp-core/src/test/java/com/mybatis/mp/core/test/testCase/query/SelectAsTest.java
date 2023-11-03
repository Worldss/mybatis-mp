package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.sql.executor.Query;
import com.mybatis.mp.core.test.mapper.SysUserMapper;
import com.mybatis.mp.core.test.model.SysUser;
import com.mybatis.mp.core.test.testCase.BaseTest;
import com.mybatis.mp.core.test.vo.SysUserVo;
import junit.framework.Assert;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

public class SelectAsTest extends BaseTest {

    @Test
    public void resultEntity() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysUserMapper sysUserMapper = session.getMapper(SysUserMapper.class);
            SysUser sysUser = sysUserMapper.get(new Query().from(SysUser.class).eq(SysUser::getId, 1));
            SysUserVo eqSysUser = new SysUserVo();
            eqSysUser.setId(1);
            eqSysUser.setUserName("admin");
            Assert.assertEquals("getById检测", eqSysUser, sysUser);
        }
    }

}
