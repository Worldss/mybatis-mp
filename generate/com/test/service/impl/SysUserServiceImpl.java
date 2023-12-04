package com.test.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.test.service.SysUserService;
import com.test.mapper.SysUserMapper;
import com.test.dao.SysUserDao;
import com.test.DO.SysUser;
import cn.mybatis.mp.core.sql.executor.chain.UpdateChain;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import cn.mybatis.mp.core.sql.executor.chain.InsertChain;
import cn.mybatis.mp.core.sql.executor.chain.DeleteChain;
import cn.mybatis.mp.core.mvc.impl.ServiceImpl;

/**
 * <p>
 *  Service 实现类
 * </p>
 *
 * @author 
 * @since 2023-12-04
 */
@Service
public class SysUserServiceImpl extends ServiceImpl implements SysUserService{

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserMapper sysUserMapper;

    private QueryChain queryChain() {
        return QueryChain.of(sysUserMapper);
    }

    private UpdateChain updateChain() {
        return UpdateChain.of(sysUserMapper);
    }

    private InsertChain insertChain(){
        return InsertChain.of(sysUserMapper);
    }

    private DeleteChain deleteChain(){
        return DeleteChain.of(sysUserMapper);
    }

}
