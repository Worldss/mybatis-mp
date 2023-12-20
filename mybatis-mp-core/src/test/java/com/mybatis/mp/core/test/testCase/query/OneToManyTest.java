package com.mybatis.mp.core.test.testCase.query;

import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import com.mybatis.mp.core.test.DO.SysRole;
import com.mybatis.mp.core.test.DO.SysUser;
import com.mybatis.mp.core.test.mapper.SysRoleMapper;
import com.mybatis.mp.core.test.testCase.BaseTest;
import com.mybatis.mp.core.test.vo.OneToManyVo;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OneToManyTest extends BaseTest {

    @Test
    public void oneToManyTest() {
        try (SqlSession session = this.sqlSessionFactory.openSession(false)) {
            SysRoleMapper sysRoleMapper = session.getMapper(SysRoleMapper.class);
            List<OneToManyVo> list = QueryChain.of(sysRoleMapper)
                    .select(SysUser.class)
                    .select(SysRole.class)
                    .from(SysRole.class)
                    .join(SysRole.class, SysUser.class, on -> on.eq(SysUser::getRole_id, SysRole::getId))
                    .setReturnType(OneToManyVo.class)
                    .list();

            System.out.println(list);
            assertEquals(OneToManyVo.class, list.get(0).getClass(), "oneToManyTest");
            assertEquals(Integer.valueOf(1), list.size(), "oneToManyTest");
            assertEquals(Integer.valueOf(2), list.get(0).getSysUserList().size(), "oneToManyTest");
            assertEquals(SysUser.class, list.get(0).getSysUserList().get(0).getClass(), "oneToManyTest");

        }
    }

}
